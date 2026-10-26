/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import com.mycompany.bicikliprojekt.SwingUI.components.StyledButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import com.mycompany.bicikliprojekt.DataBase.DataBaseManager;
import com.mycompany.bicikliprojekt.model.Bike;
import com.mycompany.bicikliprojekt.model.User;
import com.mycompany.bicikliprojekt.services.SessionManager;

/**
 *
 * @author mcsab
 */

public class ProfilePanel extends JPanel {
    
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color PRIMARY_GREEN = new Color(35, 125, 85);
    private static final Color DARK_TEXT = new Color(35, 45, 40);
    private static final Color SECONDARY_TEXT = new Color(110, 120, 115);
    private static final Color BORDER_COLOR = new Color(225, 230, 226);
    private static final Color DANGER_COLOR = new Color(195, 65, 65);
    private final JPanel cardsPanel;
    private final ArrayList<BikeItem> bikes = new ArrayList<>();
    private final DataBaseManager databaseManager = new DataBaseManager();
    // Build the profile page and load the user's bikes.
    public ProfilePanel(){
        setLayout(new BorderLayout(24, 24));
        setBackground(BACKGROUND_COLOR);
        
        setBorder(new EmptyBorder(28, 32, 28, 32));
        JLabel title = new JLabel("Saját kerékpárjaim");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(DARK_TEXT);

        JLabel subtitle = new JLabel(
                "Kezeld és tekintsd át a kerékpárjaidat"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(SECONDARY_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JButton addButton = new StyledButton(
        "+ Új kerékpár",
        PRIMARY_GREEN,
        Color.WHITE
        );

        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setOpaque(false);
        header.add(titlePanel, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);
        header.setBorder(new EmptyBorder(0, 0, 12, 0));
        cardsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        cardsPanel.setBackground(BACKGROUND_COLOR);
        cardsPanel.setBorder(new EmptyBorder(5,5,5,5));
        
        JScrollPane scrollPane = new JScrollPane(cardsPanel);

        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar().setUI(
                new com.mycompany.bicikliprojekt.SwingUI.components.ScrollBar()
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        addButton.addActionListener(e -> addBike());
        
        loadBikesFromDatabase();
        refreshCards();
    }
    // Load bikes owned by the signed-in user.
    private void loadBikesFromDatabase() {
    bikes.clear();

    User currentUser = SessionManager.getCurrentUser();

    if (currentUser == null) {
        showError("Nincs bejelentkezett felhasználó!");
        return;
    }

    System.out.println("Bejelentkezett felhasználó ID: "
        + currentUser.getUId());

    var databaseBikes = databaseManager.getBikesByUserId(
            currentUser.getUId());

    System.out.println("Betöltött kerékpárok száma: "
            + databaseBikes.size());

    for (Bike bike : databaseBikes) {
        bikes.add(new BikeItem(
                bike.getBicikliid(),
                bike.getBrand(),
                bike.getModel(),
                bike.getType(),
                bike.getSerialNumber(),
                bike.getImagePath()
        ));
    }
}
    // Rebuild the profile cards after the bike list changes.
    private void refreshCards(){
        cardsPanel.removeAll();
        
        for(BikeItem bike : bikes){
            cardsPanel.add(createBikeCard(bike));
        }
        
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    
    // Create a card for one profile bike.
    private JPanel createBikeCard(BikeItem bike) {
        return new com.mycompany.bicikliprojekt.SwingUI.components.BikeCard(
                bike.brand,
                bike.model,
                bike.type,
                bike.serialNumber,
                bike.imagePath,
                () -> editBike(bike),
                () -> deleteBike(bike)
        );
    }
    // Add a bike to the signed-in user's profile.
    private void addBike() {
        BikeItem bike = showBikeDialog(null);

        if (bike == null) {
            return;
        }

        if (serialExists(bike.serialNumber, null)) {
            showError("Ez a sorozatszám már szerepel a profilban!");
            return;
        }

        User currentUser = SessionManager.getCurrentUser();

        if (currentUser == null) {
            showError("Nincs bejelentkezett felhasználó!");
            return;
        }

        Bike newBike = new Bike(
                1,
                currentUser.getUId(),
                bike.brand,
                bike.model,
                bike.type,
                bike.serialNumber,
                bike.imagePath
        );

        databaseManager.insertBicikli(newBike);

        loadBikesFromDatabase();
        refreshCards();

        JOptionPane.showMessageDialog(
                this,
                "A kerékpár mentése megtörtént!",
                "Siker",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    // Update the selected bike's details.
    private void editBike(BikeItem bike) {
        BikeItem edited = showBikeDialog(bike);

        if (edited == null) {
            return;
        }

        if (serialExists(edited.serialNumber, bike)) {
            showError("Ez a sorozatszám már egy másik kerékpáré!");
            return;
        }

        User currentUser = SessionManager.getCurrentUser();

        if (currentUser == null) {
            showError("Nincs bejelentkezett felhasználó!");
            return;
        }

        Bike updatedBike = new Bike(
            bike.id,
            currentUser.getUId(),
            edited.brand,
            edited.model,
            edited.type,
            edited.serialNumber,
            edited.imagePath
        );

        databaseManager.updateBicikli(updatedBike);

        bike.brand = edited.brand;
        bike.model = edited.model;
        bike.type = edited.type;
        bike.serialNumber = edited.serialNumber;
        bike.imagePath = edited.imagePath;

        loadBikesFromDatabase();
        refreshCards();

        JOptionPane.showMessageDialog(
            this,
            "A kerékpár módosítása megtörtént!",
            "Siker",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Delete the selected bike after confirmation.
    private void deleteBike(BikeItem bike) {
        int answer = JOptionPane.showConfirmDialog(
            this,
            "Biztosan törlöd ezt a kerékpárt?\n"
                + bike.brand + " " + bike.model,
            "Kerékpár törlése",
            JOptionPane.YES_NO_OPTION
        );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        databaseManager.deleteBikeById(bike.id);

        loadBikesFromDatabase();
        refreshCards();
    }
    // Collect bike details and an optional image from the user.
    private BikeItem showBikeDialog(BikeItem existing) {
        JTextField brandField = new JTextField(
                existing == null ? "" : existing.brand);
        JTextField modelField = new JTextField(
                existing == null ? "" : existing.model);
        JTextField typeField = new JTextField(
                existing == null ? "" : existing.type);
        JTextField serialField = new JTextField(
                existing == null ? "" : existing.serialNumber);

        String[] imagePath = {
            existing == null ? null : existing.imagePath
        };

        JLabel imageLabel = new JLabel(
                imagePath[0] == null
                        ? "Nincs kiválasztott kép"
                        : new File(imagePath[0]).getName());
        
        imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        imageLabel.setForeground(SECONDARY_TEXT);

        JButton chooseButton = new StyledButton(
                "Kép kiválasztása", PRIMARY_GREEN, Color.WHITE);
        JButton defaultButton = new StyledButton(
                "Alapértelmezett ikon",
                new Color(235, 243, 238), PRIMARY_GREEN);

        chooseButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Képfájlok", "jpg", "jpeg", "png", "gif"));

            if (chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                imagePath[0] = file.getAbsolutePath();
                imageLabel.setText(file.getName());
            }
        });

        defaultButton.addActionListener(e -> {
            imagePath[0] = null;
            imageLabel.setText("Nincs kiválasztott kép");
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(238, 240, 238));
        panel.setBorder(new EmptyBorder(16, 12, 12, 12));
        addField(panel, "Márka:", brandField);
        addField(panel, "Modell:", modelField);
        addField(panel, "Típus:", typeField);
        addField(panel, "Sorozatszám:", serialField);

        JLabel imageTitle = new JLabel("Kerékpár képe:");
        imageTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        imageTitle.setForeground(DARK_TEXT);

        panel.add(imageTitle);
        panel.add(Box.createVerticalStrut(5));
        panel.add(chooseButton);
        panel.add(Box.createVerticalStrut(5));
        panel.add(defaultButton);
        panel.add(Box.createVerticalStrut(5));
        panel.add(imageLabel);

        JButton saveButton = new StyledButton(
                "Mentés", PRIMARY_GREEN, Color.WHITE);
        JButton cancelButton = new StyledButton(
                "Mégse", new Color(235, 243, 238), PRIMARY_GREEN);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(CARD_COLOR);
        buttons.add(cancelButton);
        buttons.add(saveButton);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(CARD_COLOR);
        content.add(panel, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(this),
                existing == null
                        ? "Új kerékpár hozzáadása"
                        : "Kerékpár módosítása",
                Dialog.ModalityType.APPLICATION_MODAL);

        dialog.setContentPane(content);
        dialog.getRootPane().setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR));

        BikeItem[] result = {null};

        saveButton.addActionListener(e -> {
            String brand = brandField.getText().trim();
            String model = modelField.getText().trim();
            String type = typeField.getText().trim();
            String serial = serialField.getText().trim();

            if (brand.isEmpty() || serial.isEmpty()) {
                showError("A márka és a sorozatszám megadása kötelező!");
                return;
            }

            result[0] = new BikeItem(
                    existing == null ? -1 : existing.id,
                    brand,
                    model,
                    type,
                    serial,
                    imagePath[0]
            );
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setMinimumSize(new Dimension(350, 0));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }

    // Add a styled label and text field to the form.
    private void addField(
        JPanel panel, String label, JTextField field) {

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        fieldLabel.setForeground(DARK_TEXT);

        panel.add(fieldLabel);
        panel.add(Box.createVerticalStrut(5));

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(DARK_TEXT);
        field.setBackground(Color.WHITE);
        field.setCaretColor(PRIMARY_GREEN);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 224, 218)),
                new EmptyBorder(5, 7, 5, 7)
        ));
        field.setPreferredSize(new Dimension(300, 34));
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 34));

        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    // Check whether another profile bike uses this serial number.
    private boolean serialExists(String serial, BikeItem excluded) {
        for (BikeItem bike : bikes) {
            if (bike != excluded
                    && bike.serialNumber.equalsIgnoreCase(serial)) {
                return true;
            }
        }

        return false;
    }
    // Display a warning message to the user.
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Figyelmeztetés",
                JOptionPane.WARNING_MESSAGE
        );
    }
    // Store the data displayed for a profile bike.
    private static class BikeItem {

        private int id;
        private String brand;
        private String model;
        private String type;
        private String serialNumber;
        private String imagePath;

        private BikeItem(
                int id,
                String brand,
                String model,
                String type,
                String serialNumber,
                String imagePath) {

            this.id = id;
            this.brand = brand;
            this.model = model;
            this.type = type;
            this.serialNumber = serialNumber;
            this.imagePath = imagePath;
    }
}

    
    private static class BikeIcon implements Icon {

        private final int width;
        private final int height;
        // Set the size of the default bike icon.
        private BikeIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }
        // Return the configured icon width.
        @Override
        public int getIconWidth() {
            return width;
        }
        // Return the configured icon height.
        @Override
        public int getIconHeight() {
            return height;
        }
        // Draw the default bike icon.
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

