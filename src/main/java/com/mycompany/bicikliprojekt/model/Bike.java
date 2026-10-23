/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public class Bike extends BaseBike {
    private int userId;
    private String type;

    public Bike ( int id, int userId, String brand, String model, String type, String serialNumber) 
    {
        super(id, brand, model, serialNumber);
        setUserId(userId);
        setType(type);
    }

    public int getUserId() { return userId; }
    public String getType() { return type; }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        this.userId = userId;
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty or null.");
        }
        this.type = type.trim();
    }
}
