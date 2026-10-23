/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import com.mycompany.bicikliprojekt.SwingUI.components.BikeIcon;
import com.mycompany.bicikliprojekt.SwingUI.components.ScrollBar;
import com.mycompany.bicikliprojekt.SwingUI.components.StyledButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.io.File;

/**
 *
 * @author mcsab
 */
public class ForSalePanel extends JPanel {
    private final JPanel cardsPanel;
    private final ArrayList<Bike> bikes = new ArrayList<>();

    public ForSalePanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Eladó kerékpárok");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JButton addButton = new StyledButton(
        "+ Kerékpár hozzáadása",
        new Color(35, 125, 85),
        Color.WHITE
        );

        JPanel header = new JPanel(new BorderLayout());
        header.add(title, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);

        cardsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        cardsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUI(
                new ScrollBar()
        );
        scrollPane.getHorizontalScrollBar().setUI(
                new ScrollBar()
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Ideiglenes próbaadatok
        bikes.add(new Bike(
            "Trek", 
            "Marlin 5",
            "MTB",
            "Használt",
            1800,
            "Megbízható hegyikerékpár, jó állapotban.",
            null
        ));
        bikes.add(new Bike(
                "Trek", "Marlin 5", "MTB",
                "Használt", 1800,
                "Megbízható hegyikerékpár, jó állapotban.",
                null
        ));

        bikes.add(new Bike(
                "Giant", "Escape 3", "Városi",
                "Újszerű", 1200,
                "Kényelmes kerékpár városi használatra.",
                null
        ));

        bikes.add(new Bike(
                "Merida", "Dual Thrust", "MTN",
                "Használt", 950,
                "Kedvező árú kerékpár mindennapi használatra.",
                null
        ));
        

        addButton.addActionListener(e -> addBike());

        refreshCards();
    }

    private void refreshCards() {
        cardsPanel.removeAll();

        for (Bike bike : bikes) {
            cardsPanel.add(createBikeCard(bike));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel createBikeCard(Bike bike) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 215, 215)
                ),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 140));

        if (bike.imagePath != null
                && new File(bike.imagePath).isFile()) {

            ImageIcon original = new ImageIcon(bike.imagePath);

            Image scaled = original.getImage().getScaledInstance(
                    160, 120, Image.SCALE_SMOOTH
            );

            imageLabel.setIcon(new ImageIcon(scaled));

        } else {
            imageLabel.setIcon(new BikeIcon(120, 120));
        }

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(bike.brand + " " + bike.model);
        name.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel type = new JLabel("Típus: " + bike.type);
        JLabel condition = new JLabel("Állapot: " + bike.condition);

        JLabel price = new JLabel(
                String.format("%,.0f lej", bike.price)
        );
        price.setFont(new Font("SansSerif", Font.BOLD, 20));
        price.setForeground(new Color(35, 125, 75));

        JTextArea description = new JTextArea(bike.description);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setFocusable(false);
        description.setOpaque(false);
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setRows(3);

        details.add(name);
        details.add(Box.createVerticalStrut(8));
        details.add(type);
        details.add(Box.createVerticalStrut(4));
        details.add(condition);
        details.add(Box.createVerticalStrut(8));
        details.add(price);
        details.add(Box.createVerticalStrut(8));
        details.add(description);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 5, 0)
        );
        buttons.setOpaque(false);

        JButton editButton = new StyledButton(
        "Módosítás",
        new Color(235, 242, 237),
        new Color(35, 125, 85)
        );

        JButton deleteButton = new StyledButton(
        "Törlés",
        new Color(250, 235, 235),
        new Color(200, 55, 55)
        );

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
        Bike bike = showBikeDialog(null);

        if (bike != null) {
            bikes.add(bike);
            refreshCards();
        }
    }

    private void editBike(Bike bike) {
        Bike edited = showBikeDialog(bike);

        if (edited != null) {
            bike.brand = edited.brand;
            bike.model = edited.model;
            bike.type = edited.type;
            bike.condition = edited.condition;
            bike.price = edited.price;
            bike.description = edited.description;
            bike.imagePath = edited.imagePath;

            refreshCards();
        }
    }

    private void deleteBike(Bike bike) {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Biztosan törlöd ezt a hirdetést?",
                "Hirdetés törlése",
                JOptionPane.YES_NO_OPTION
        );

        if (answer == JOptionPane.YES_OPTION) {
            bikes.remove(bike);
            refreshCards();
        }
    }

    private Bike showBikeDialog(Bike existing) {
        JTextField brandField = new JTextField(
                existing == null ? "" : existing.brand
        );

        JTextField modelField = new JTextField(
                existing == null ? "" : existing.model
        );

        JTextField typeField = new JTextField(
                existing == null ? "" : existing.type
        );

        String[] conditions = {
            "Új", "Újszerű", "Használt", "Felújítandó"
        };

        JComboBox<String> conditionBox =
                new JComboBox<>(conditions);
        
        conditionBox.setBackground(Color.WHITE);
        conditionBox.setForeground(new Color(35, 50, 60));
        conditionBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        conditionBox.setFocusable(false);
        conditionBox.setBorder(
                BorderFactory.createLineBorder(new Color(215, 225, 219))
        );

        JTextField priceField = new JTextField(
                existing == null ? "" : String.valueOf(existing.price)
        );

        JTextArea descriptionArea = new JTextArea(
                existing == null ? "" : existing.description,
                4, 20
        );
        String[] selectedImage = {
    existing == null ? null : existing.imagePath};

        JLabel imageStatus = new JLabel(
                selectedImage[0] == null
                        ? "Alapértelmezett kerékpárikon"
                        : new File(selectedImage[0]).getName()
        );
        
        JButton chooseImageButton = new StyledButton(
                "Kép kiválasztása",
                new Color(35, 125, 85),
                Color.WHITE
        );

        JButton removeImageButton = new StyledButton(
                "Alapértelmezett ikon",
                new Color(235, 242, 237),
                new Color(35, 125, 85)
        );

        chooseImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Kerékpár képének kiválasztása");

            chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Képfájlok (JPG, PNG, GIF)",
                            "jpg", "jpeg", "png", "gif"
                    )
            );

            if (chooser.showOpenDialog(this)
                    == JFileChooser.APPROVE_OPTION) {

                selectedImage[0] =
                        chooser.getSelectedFile().getAbsolutePath();

                imageStatus.setText(
                        chooser.getSelectedFile().getName()
                );
            }
        });

        removeImageButton.addActionListener(e -> {
            selectedImage[0] = null;
            imageStatus.setText("Alapértelmezett kerékpárikon");
        });

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(
                new BoxLayout(imagePanel, BoxLayout.Y_AXIS)
        );
        imagePanel.setOpaque(false);

        imagePanel.add(chooseImageButton);
        imagePanel.add(Box.createVerticalStrut(8));
        imagePanel.add(removeImageButton);
        imagePanel.add(Box.createVerticalStrut(8));

        imageStatus.setForeground(new Color(105, 120, 128));
        imagePanel.add(imageStatus);

        if (existing != null) {
            conditionBox.setSelectedItem(existing.condition);
        }
        
        Color fieldBorderColor = new Color(215, 225, 219);
        
        JTextField[] textFields = {
        brandField,
        modelField,
        typeField,
        priceField
        };

    for (JTextField field : textFields) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fieldBorderColor),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    conditionBox.setFont(new Font("SansSerif", Font.PLAIN, 14));

    descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
    descriptionArea.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        
        Object[] fields = {
            "Márka:", brandField,
            "Modell:", modelField,
            "Típus:", typeField,
            "Állapot:", conditionBox,
            "Ár (lej):", priceField,
            "Leírás:", new JScrollPane(descriptionArea),
            "Kép:", imagePanel
        };

        JOptionPane optionPane = new JOptionPane(
                fields,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION
        );

        optionPane.setOptions(new Object[]{});
        optionPane.setInitialValue(null);

        JDialog dialog = optionPane.createDialog(
                this,
                existing == null ? "Új hirdetés" : "Hirdetés módosítása"
        );

        JButton saveButton = new StyledButton(
                "Mentés",
                new Color(35, 125, 85),
                Color.WHITE
        );

        JButton cancelButton = new StyledButton(
                "Mégse",
                new Color(235, 242, 237),
                new Color(35, 125, 85)
        );

        final boolean[] saved = {false};

        saveButton.addActionListener(e -> {
            saved[0] = true;
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 8)
        );
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        JPanel dialogContent = new JPanel(new BorderLayout(0, 12));
        dialogContent.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        dialogContent.setBackground(new Color(248, 250, 249));
        dialogContent.add(optionPane, BorderLayout.CENTER);
        dialogContent.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(dialogContent);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (!saved[0]) {
            return null;
        }

        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String type = typeField.getText().trim();
        String condition =
                (String) conditionBox.getSelectedItem();
        String description = descriptionArea.getText().trim();

        if (brand.isEmpty() || model.isEmpty()
                || priceField.getText().trim().isEmpty()) {
            showError("A márka, modell és ár megadása kötelező!");
            return null;
        }

        double price;

        try {
            price = Double.parseDouble(
                    priceField.getText().trim().replace(',', '.')
            );

            if (!Double.isFinite(price) || price <= 0) {
                showError("Az árnak pozitív számnak kell lennie!");
                return null;
            }

        } catch (NumberFormatException ex) {
            showError("Az ár mezőbe érvényes számot írj!");
            return null;
        }

        return new Bike(
                brand, model, type, condition, price, description, selectedImage[0]
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Hiba",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private static class Bike {
        private String brand;
        private String model;
        private String type;
        private String condition;
        private double price;
        private String description;
        private String imagePath;

        private Bike(
                String brand,
                String model,
                String type,
                String condition,
                double price,
                String description,
                String imagePath) {

            this.brand = brand;
            this.model = model;
            this.type = type;
            this.condition = condition;
            this.price = price;
            this.description = description;
            this.imagePath = imagePath;
        }
    }
}

    