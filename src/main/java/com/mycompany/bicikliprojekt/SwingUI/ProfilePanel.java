/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author mcsab
 */
public class ProfilePanel extends JPanel {
    
    private final JPanel cardsPanel;
    private final ArrayList<BikeItem> bikes = new ArrayList<>();
    
    public ProfilePanel(){
        setLayout(new BorderLayout(15,15));
        setBorder(new EmptyBorder(20,20,20,20));
        
        JLabel title = new JLabel("Saját kerékpárjaim");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        JButton addButton = new JButton("+ Új kerékpár");
        
        JPanel header = new JPanel(new BorderLayout());
        header.add(title, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        
        cardsPanel = new JPanel(new GridLayout(0,2,15,15));
        cardsPanel.setBorder(new EmptyBorder(5,5,5,5));
        
        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        addButton.addActionListener(e -> addBike());
        
        //Temporary test datas
        bikes.add(new BikeItem(
                "Trek", "Marlin 5", "MTB", "BK001", null
        ));

        bikes.add(new BikeItem(
                "Giant", "Escape 3", "Városi", "BK002", null
        ));

        refreshCards();
    }
    
    private void refreshCards(){
        cardsPanel.removeAll();
        
        for(BikeItem bike : bikes){
            cardsPanel.add(createBikeCard(bike));
        }
        
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    
    private JPanel createBikeCard(BikeItem bike){
        JPanel card = new JPanel(new BorderLayout(8, 8));

        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)
                ),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(220, 150));

        if (bike.imagePath != null
                && new File(bike.imagePath).isFile()) {

            ImageIcon original = new ImageIcon(bike.imagePath);

            Image scaled = original.getImage().getScaledInstance(
                    220, 150, Image.SCALE_SMOOTH
            );

            imageLabel.setIcon(new ImageIcon(scaled));

        } else {
            imageLabel.setIcon(new BikeIcon(100, 100));
        }

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(bike.brand + " " + bike.model);
        name.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel type = new JLabel("Típus: " + bike.type);
        JLabel serial = new JLabel("Sorozatszám: " + bike.serialNumber);

        details.add(name);
        details.add(Box.createVerticalStrut(8));
        details.add(type);
        details.add(Box.createVerticalStrut(4));
        details.add(serial);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 5, 0)
        );
        buttons.setOpaque(false);

        JButton editButton = new JButton("Módosítás");
        JButton deleteButton = new JButton("Törlés");

        editButton.addActionListener(e -> editBike(bike));
        deleteButton.addActionListener(e -> deleteBike(bike));

        buttons.add(editButton);
        buttons.add(deleteButton);

        card.add(imageLabel, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }
    
     private void addBike() {
        BikeItem bike = showBikeDialog(null);

        if (bike != null) {
            if (serialExists(bike.serialNumber, null)) {
                showError("Ez a sorozatszám már szerepel a profilban!");
                return;
            }

            bikes.add(bike);
            refreshCards();
        }
    }

    private void editBike(BikeItem bike) {
        BikeItem edited = showBikeDialog(bike);

        if (edited != null) {
            if (serialExists(edited.serialNumber, bike)) {
                showError("Ez a sorozatszám már egy másik kerékpáré!");
                return;
            }

            bike.brand = edited.brand;
            bike.model = edited.model;
            bike.type = edited.type;
            bike.serialNumber = edited.serialNumber;
            bike.imagePath = edited.imagePath;

            refreshCards();
        }
    }

    private void deleteBike(BikeItem bike) {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Biztosan törlöd ezt a kerékpárt?\n"
                        + bike.brand + " " + bike.model,
                "Kerékpár törlése",
                JOptionPane.YES_NO_OPTION
        );

        if (answer == JOptionPane.YES_OPTION) {
            bikes.remove(bike);
            refreshCards();
        }
    }

    private BikeItem showBikeDialog(BikeItem existing) {
        JTextField brandField = new JTextField(
                existing == null ? "" : existing.brand
        );

        JTextField modelField = new JTextField(
                existing == null ? "" : existing.model
        );

        JTextField typeField = new JTextField(
                existing == null ? "" : existing.type
        );

        JTextField serialField = new JTextField(
                existing == null ? "" : existing.serialNumber
        );

        String[] selectedImage = {
            existing == null ? null : existing.imagePath
        };

        JLabel imageStatus = new JLabel(
                selectedImage[0] == null
                        ? "Alapértelmezett kerékpárikon"
                        : new File(selectedImage[0]).getName()
        );

        JButton chooseImageButton = new JButton("Kép kiválasztása");
        JButton removeImageButton = new JButton("Alapértelmezett ikon");

        chooseImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Kerékpár képének kiválasztása");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Képfájlok (JPG, PNG, GIF)",
                    "jpg", "jpeg", "png", "gif"
            ));

            if (chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                selectedImage[0] =
                        chooser.getSelectedFile().getAbsolutePath();

                imageStatus.setText(chooser.getSelectedFile().getName());
            }
        });

        removeImageButton.addActionListener(e -> {
            selectedImage[0] = null;
            imageStatus.setText("Alapértelmezett kerékpárikon");
        });

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.add(chooseImageButton);
        imagePanel.add(Box.createVerticalStrut(5));
        imagePanel.add(removeImageButton);
        imagePanel.add(Box.createVerticalStrut(5));
        imagePanel.add(imageStatus);

        Object[] fields = {
            "Márka:", brandField,
            "Modell:", modelField,
            "Típus:", typeField,
            "Sorozatszám:", serialField,
            "Kerékpár képe:", imagePanel
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                existing == null
                        ? "Új kerékpár hozzáadása"
                        : "Kerékpár módosítása",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String type = typeField.getText().trim();
        String serial = serialField.getText().trim();

        if (brand.isEmpty() || serial.isEmpty()) {
            showError("A márka és a sorozatszám megadása kötelező!");
            return null;
        }

        return new BikeItem(
                brand, model, type, serial, selectedImage[0]
        );
    }

    private boolean serialExists(String serial, BikeItem excluded) {
        for (BikeItem bike : bikes) {
            if (bike != excluded
                    && bike.serialNumber.equalsIgnoreCase(serial)) {
                return true;
            }
        }

        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Figyelmeztetés",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private static class BikeItem {

        private String brand;
        private String model;
        private String type;
        private String serialNumber;
        private String imagePath;

        private BikeItem(
                String brand,
                String model,
                String type,
                String serialNumber,
                String imagePath) {

            this.brand = brand;
            this.model = model;
            this.type = type;
            this.serialNumber = serialNumber;
            this.imagePath = imagePath;
        }
    }

    // Saját rajzolt ikon, így nem kell hozzá külön képfájl.
    private static class BikeIcon implements Icon {

        private final int width;
        private final int height;

        private BikeIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public void paintIcon(
                Component component,
                Graphics graphics,
                int x,
                int y) {

            Graphics2D g = (Graphics2D) graphics.create();

            g.translate(x, y);
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g.setColor(new Color(225, 232, 238));
            g.fillRoundRect(0, 0, width, height, 18, 18);

            g.setColor(new Color(65, 85, 100));
            g.setStroke(new BasicStroke(3));

            int leftX = 20;
            int rightX = width - 20;
            int wheelY = height - 25;
            int radius = 17;

            g.draw(new Ellipse2D.Double(
                    leftX - radius, wheelY - radius,
                    radius * 2, radius * 2
            ));

            g.draw(new Ellipse2D.Double(
                    rightX - radius, wheelY - radius,
                    radius * 2, radius * 2
            ));

            int crankX = width / 2;
            int crankY = wheelY - 5;

            g.draw(new Line2D.Double(
                    leftX, wheelY, crankX, crankY
            ));

            g.draw(new Line2D.Double(
                    crankX, crankY, rightX, wheelY
            ));

            g.draw(new Line2D.Double(
                    leftX, wheelY, crankX - 5, wheelY - 25
            ));

            g.draw(new Line2D.Double(
                    crankX - 5, wheelY - 25, rightX, wheelY
            ));

            g.draw(new Line2D.Double(
                    crankX - 5, wheelY - 25,
                    crankX + 5, wheelY - 25
            ));

            g.draw(new Line2D.Double(
                    crankX - 5, wheelY - 25,
                    crankX - 12, wheelY - 32
            ));

            g.dispose();
        }
    }
}

