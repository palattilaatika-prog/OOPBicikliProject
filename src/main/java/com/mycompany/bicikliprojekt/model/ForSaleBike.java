/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public class ForSaleBike extends Bike{
    private int bikeId;
    private String condition;
    private double price;
    private String imagePath;
    private String description;
    private String listedDate;

    public ForSaleBike(int id, int userId, String brand, String model, String type, String serialNumber,
                       int bikeId, String condition, double price, String imagePath, String description, String listedDate) {
        super(id, userId, brand, model, type, serialNumber);
        setBikeId(bikeId); //neceserry for database in future
        setCondition(condition);
        setPrice(price);
        setImagePath(imagePath);
        setDescription(description);
        setListedDate(listedDate);
    }

    public int getBikeId() { return bikeId; }
    public String getCondition() { return condition; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }
    public String getListedDate() { return listedDate; }

    public void setBikeId(int bikeId) {
        if (bikeId <= 0) throw new IllegalArgumentException("Bike ID must be positive.");
        this.bikeId = bikeId;
    }

    public void setCondition(String condition) {
        if (condition == null || condition.trim().isEmpty()) throw new IllegalArgumentException("Condition cannot be empty.");
        this.condition = condition.trim();
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath != null ? imagePath.trim() : "";
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : "";
    }

    public void setListedDate(String listedDate) {
        if (listedDate == null || listedDate.trim().isEmpty()) throw new IllegalArgumentException("Listed date cannot be empty.");
        this.listedDate = listedDate.trim();
    }
}
