/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public abstract class BaseBike {
    private int id;
    private String brand;
    private String model;
    private String serialNumber;
    public BaseBike(int id, String brand, String model, String serialNumber) {
        setId(id);
        setBrand(brand);
        setModel(model);
        setSerialNumber(serialNumber);
    }
    //get-er set-ers with validation
    public int getBicikliid(){return id;}
    public String getBrand(){return brand;}
    public String getModel(){return model;}
    public String getSerialNumber(){return serialNumber;}
    
   public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Bicycle ID must be a positive integer.");
        }
        this.id = id;
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
