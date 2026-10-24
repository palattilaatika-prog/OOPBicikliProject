/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Denes
 */
public class User {
    int id;
    String name;
    String email;
    String password;
    String phoneNumber;
    List<Bike> bikes; 
    // The bikes of the user (list)
    public User(int id, String name, String email, String password) {
        this(id, name, email, password, null);
    }

    public User(int id, String name, String email,String password,String phoneNumber) {
        setUId(id);
        setUname(name);
        setEmail(email);
        this.password = password;
        setPhoneNumber(phoneNumber);
        this.bikes = new ArrayList<>();
    }

    // Adding bike to user
    public void addBike(Bike bike) {
        if (bike != null) {
            this.bikes.add(bike);
        }
    }

    // Removeing bike from user
    public void removeBike(Bike bike) {
        this.bikes.remove(bike);
    }

    // List of bikes (read-only)
    public List<Bike> getBikes() {
        return Collections.unmodifiableList(bikes);
    }
    public void setUId(int userId){
            if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        this.id = userId;
}
    public void setUname(String username)
    {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty or null.");
        }
        this.name = username.trim();
    }
    public void setEmail(String email)
    {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Serial number cannot be empty or null.");
        }
        //has to be a real email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
        throw new IllegalArgumentException("Incorrect email format.");
           }
        this.email = email.trim();
    }
    
    public void setPassword(String password) 
    {
        if(password==null||password.trim().isEmpty())
            
        {throw new IllegalArgumentException("Type cannot be empty or null.");}
        if(password.length()<=8)
        {throw new IllegalArgumentException("Password must be at least 8 characters ");}
        this.password = password; 
    }
    
    public String getPassword() { return password; }
    public int getUId(){return id;}
    public String getUname(){return name;}
    public String getEmail(){return email;}
    public List<Bike> getBiciklik(){return bikes;}
    
    public String getPhoneNumber() {
    return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
        this.phoneNumber = null;
        return;
    }

    String normalized = phoneNumber.trim();
    String digits = normalized.replaceAll("\\D", "");

    if (!normalized.matches("\\+?[0-9()\\s.-]+")
            || digits.length() < 7
            || digits.length() > 15) {
        throw new IllegalArgumentException(
                "Adj meg érvényes telefonszámot 7–15 számjeggyel!"
        );
    }

    this.phoneNumber = normalized;
}
}