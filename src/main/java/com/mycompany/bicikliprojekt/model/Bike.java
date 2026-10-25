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
    private String imagePath;

        public Bike(
                int id,
                int userId,
                String brand,
                String model,
                String type,
                String serialNumber
        ) {
            this(id, userId, brand, model, type, serialNumber, null);
        }

        public Bike(
                int id,
                int userId,
                String brand,
                String model,
                String type,
                String serialNumber,
                String imagePath
        ) {
            super(id, brand, model, serialNumber);
            setUserId(userId);
            setType(type);
            setImagePath(imagePath);
        }

        public int getUserId() {
            return userId;
        }

        public String getType() {
            return type;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setUserId(int userId) {
            if (userId <= 0) {
                throw new IllegalArgumentException("User ID must be positive.");
            }
            this.userId = userId;
        }

        public void setType(String type) {
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("Type cannot be empty or null.");
            }
            this.type = type.trim();
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath == null ? "" : imagePath.trim();
        }
    }
