/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public class Bicikli {
    private int id;
    private int userID;
    private String brand;
    private String model;
    private String type;
    private String serialNumber;
    public Bicikli(int id, int userID, String brand, String model, String type, String serialNumber)
    {
        setId(id);
        setUserID(userID);
        setBrand(brand);
        setModel(model);
        setType(type);
        setSerialNumber(serialNumber);
        
    }
    //get-er set-ers with validation
    public int getBicikliid(){return id;}
    public int getuserID(){return userID;}
    public String getBrand(){return brand;}
    public String getModel(){return model;}
    public String getType(){return type;}
    public String getSerialNumber(){return serialNumber;}
    
   public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Bicycle ID must be a positive integer.");
        }
        this.id = id;
    }

    public void setUserID(int userID) {
        if (userID <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        this.userID = userID;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty or null.");
        }
        this.brand = brand.trim();
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty or null.");
        }
        this.model = model.trim();
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty or null.");
        }
        this.type = type.trim();
    }

    public void setSerialNumber(String serialNumber) {
        if (serialNumber == null || serialNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Serial number cannot be empty or null.");
        }
        //alphanumeric only (A-Z, 0-9)
        if (!serialNumber.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Serial number must contain only letters and numbers.");
        }
        this.serialNumber = serialNumber.trim();
    }
}
