/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.DataBase;
import com.mycompany.bicikliprojekt.model.Bike;
import com.mycompany.bicikliprojekt.model.ForSaleBike;
import com.mycompany.bicikliprojekt.model.StolenBike;
import com.mycompany.bicikliprojekt.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Denes
 */
public class DataBaseManager {
    //CRUD operations
    // inserts
    public void insertUser(User user) {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUname());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void insertBicikli(Bike bike) {
        String sql = "INSERT INTO bikes(userId, brand, model, type, serialNumber) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bike.getUserId());
            pstmt.setString(2, bike.getBrand());
            pstmt.setString(3, bike.getModel());
            pstmt.setString(4, bike.getType());
            pstmt.setString(5, bike.getSerialNumber());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting bicycle: " + e.getMessage());
        }
    }

    public void insertEladoBicikli(ForSaleBike bike) {
        String sql = "INSERT INTO for_sale_bikes(bikeId, condition, price, imagePath, description, listedDate) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bike.getBikeId());
            pstmt.setString(2, bike.getCondition());
            pstmt.setDouble(3, bike.getPrice());
            pstmt.setString(4, bike.getImagePath());
            pstmt.setString(5, bike.getDescription());
            pstmt.setString(6, bike.getListedDate());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting bike for sale: " + e.getMessage());
        }
    }

    public void insertLopottBicikli(StolenBike bike) {
        String sql = "INSERT INTO stolen_bikes(serialNumber, brand, model, reportedDate, source) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bike.getSerialNumber());
            pstmt.setString(2, bike.getBrand());
            pstmt.setString(3, bike.getModel());
            pstmt.setString(4, bike.getReportedDate());
            pstmt.setString(5, bike.getSource());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting stolen bike: " + e.getMessage());
        }
    }
    //adds stolenbikes 
    public void insertAllLopottBicikli(List<StolenBike> bikes) {
    String sql = "INSERT INTO stolen_bikes(serialNumber, brand, model, reportedDate, source) VALUES(?, ?, ?, ?, ?)";

    try (Connection conn = DataBase.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (StolenBike bike : bikes) {
            pstmt.setString(1, bike.getSerialNumber());
            pstmt.setString(2, bike.getBrand());
            pstmt.setString(3, bike.getModel());
            pstmt.setString(4, bike.getReportedDate());
            pstmt.setString(5, bike.getSource());
            pstmt.addBatch();
        }

        pstmt.executeBatch(); //Sends in whole to db

    } catch (SQLException e) {
        System.err.println("Error batch inserting stolen bikes: " + e.getMessage());
    }
}

    //read operations
    //all bikes for sale
    public List<ForSaleBike> getAllEladoBicikli() {
        List<ForSaleBike> forSaleBikes = new ArrayList<>();
        String sql = "SELECT f.*, b.userId, b.brand, b.model, b.type, b.serialNumber "
                   + "FROM for_sale_bikes f "
                   + "JOIN bikes b ON f.bikeId = b.id";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ForSaleBike bike = new ForSaleBike(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getString("serialNumber"),
                        rs.getInt("bikeId"),
                        rs.getString("condition"),
                        rs.getDouble("price"),
                        rs.getString("imagePath"),
                        rs.getString("description"),
                        rs.getString("listedDate")
                );
                forSaleBikes.add(bike);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all bikes for sale: " + e.getMessage());
        }

        return forSaleBikes;
    }
    //all stolen bikes
    public List<StolenBike> getAllLopottBicikli() {
        List<StolenBike> stolenBikes = new ArrayList<>();
        String sql = "SELECT * FROM stolen_bikes";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                StolenBike bike = new StolenBike(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("serialNumber"),
                        rs.getString("reportedDate"),
                        rs.getString("source")
                );
                stolenBikes.add(bike);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all stolen bikes: " + e.getMessage());
        }

        return stolenBikes;
    }
    //bikes of a user

    public List<Bike> getBikesByUserId(int userId) {
        List<Bike> userBikes = new ArrayList<>();
        String sql = "SELECT * FROM bikes WHERE userId = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Bike bike = new Bike(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getString("serialNumber")
                );
                userBikes.add(bike);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user bikes: " + e.getMessage());
        }

        return userBikes;
    }
  //finding a user
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }

        return user;
    }
    // all users to list in UI
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }

        return users;
    }
    //searching a bike for sale
    public ForSaleBike getEladoBicikliById(int listingId) {
        String sql = "SELECT f.*, b.userId, b.brand, b.model, b.type, b.serialNumber "
                   + "FROM for_sale_bikes f "
                   + "JOIN bikes b ON f.bikeId = b.id "
                   + "WHERE f.id = ?";
        ForSaleBike bike = null;

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, listingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bike = new ForSaleBike(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getString("serialNumber"),
                        rs.getInt("bikeId"),
                        rs.getString("condition"),
                        rs.getDouble("price"),
                        rs.getString("imagePath"),
                        rs.getString("description"),
                        rs.getString("listedDate")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching bike for sale: " + e.getMessage());
        }

        return bike;
    }
    //finding stolenbike
    public StolenBike getLopottBicikliById(int stolenId) {
        String sql = "SELECT * FROM stolen_bikes WHERE id = ?";
        StolenBike bike = null;

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stolenId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bike = new StolenBike(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("serialNumber"),
                        rs.getString("reportedDate"),
                        rs.getString("source")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error fetching stolen bike: " + e.getMessage());
        }

        return bike;
    }
    //searching a stolen bike by serialnumber
    public List<StolenBike> searchLopottBicikliBySerialNumber(String serialNumber) {
        List<StolenBike> result = new ArrayList<>();
        String sql = "SELECT * FROM stolen_bikes WHERE serialNumber LIKE ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + serialNumber + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                StolenBike bike = new StolenBike(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("serialNumber"),
                        rs.getString("reportedDate"),
                        rs.getString("source")
                );
                result.add(bike);
            }

        } catch (SQLException e) {
            System.err.println("Error searching stolen bikes: " + e.getMessage());
        }

        return result;
    }
    //update
    //updates the whole bike
    public void updateBicikli(Bike bike) {
        String sql = "UPDATE bikes SET brand = ?, model = ?, type = ?, serialNumber = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bike.getBrand());
            pstmt.setString(2, bike.getModel());
            pstmt.setString(3, bike.getType());
            pstmt.setString(4, bike.getSerialNumber());
            pstmt.setInt(5, bike.getBicikliid());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating bike: " + e.getMessage());
        }
    }
    //updates the type of the bike
    public void updateBikeType(int bikeId, String newType) {
        String sql = "UPDATE bikes SET type = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newType);
            pstmt.setInt(2, bikeId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating bike type: " + e.getMessage());
        }
    }
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUname());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getUId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }
    public void updateEladoBicikli(ForSaleBike bike) {
        String sql = "UPDATE for_sale_bikes SET condition = ?, price = ?, imagePath = ?, description = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bike.getCondition());
            pstmt.setDouble(2, bike.getPrice());
            pstmt.setString(3, bike.getImagePath());
            pstmt.setString(4, bike.getDescription());
            pstmt.setInt(5, bike.getBicikliid()); // Using listing ID
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating bike for sale: " + e.getMessage());
        }
    }
    public void updateLopottBicikli(StolenBike bike) {
        String sql = "UPDATE stolen_bikes SET serialNumber = ?, brand = ?, model = ?, reportedDate = ?, source = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bike.getSerialNumber());
            pstmt.setString(2, bike.getBrand());
            pstmt.setString(3, bike.getModel());
            pstmt.setString(4, bike.getReportedDate());
            pstmt.setString(5, bike.getSource());
            pstmt.setInt(6, bike.getBicikliid());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating stolen bike: " + e.getMessage());
        }
    }
    
    //delete

    public void deleteBikeById(int bikeId) {
        String sql = "DELETE FROM bikes WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bikeId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting bike: " + e.getMessage());
        }
    }
    public void deleteUserById(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " user(s).");

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
    public void deleteEladoBicikliById(int forSaleBikeId) {
        String sql = "DELETE FROM for_sale_bikes WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, forSaleBikeId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " sale listing(s).");

        } catch (SQLException e) {
            System.err.println("Error deleting sale listing: " + e.getMessage());
        }
    }
    public void deleteLopottBicikliById(int stolenBikeId) {
        String sql = "DELETE FROM stolen_bikes WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stolenBikeId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " stolen bike record(s).");

        } catch (SQLException e) {
            System.err.println("Error deleting stolen bike record: " + e.getMessage());
        }
    }
}
