/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.Icon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author mcsab
 */
public class BikeCard extends RoundedPanel {

    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color PRIMARY_GREEN = new Color(35, 125, 85);
    private static final Color DARK_TEXT = new Color(35, 45, 40);
    private static final Color SECONDARY_TEXT = new Color(110, 120, 115);
    private static final Color DANGER_COLOR = new Color(195, 65, 65);

    public BikeCard(
            String brand,
            String model,
            String type,
            String serialNumber,
            String imagePath,
            Runnable onEdit,
            Runnable onDelete) {

        super(18);

        setLayout(new BorderLayout(0, 0));
        setBackground(CARD_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 12, 10));

        // Image area
        JPanel imageContainer = new JPanel(new BorderLayout());
        imageContainer.setBackground(new Color(237, 242, 238));
        imageContainer.setPreferredSize(new Dimension(300, 190));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 190));

        if (imagePath != null && new File(imagePath).isFile()) {
            ImageIcon original = new ImageIcon(imagePath);

            // Preserve the image aspect ratio instead of stretching it.
            int sourceWidth = original.getIconWidth();
            int sourceHeight = original.getIconHeight();

            if (sourceWidth > 0 && sourceHeight > 0) {
                double scale = Math.min(
                        280.0 / sourceWidth,
                        170.0 / sourceHeight
                );

                int width = Math.max(1, (int) (sourceWidth * scale));
                int height = Math.max(1, (int) (sourceHeight * scale));

                Image scaled = original.getImage().getScaledInstance(
                        width,
                        height,
                        Image.SCALE_SMOOTH
                );

                imageLabel.setIcon(new ImageIcon(scaled));
            }
        } else {
            imageLabel.setIcon(new BikeIcon(180, 125));
        }

        imageContainer.add(imageLabel, BorderLayout.CENTER);

        // Bike details
        JPanel details = new JPanel();
        details.setBackground(CARD_COLOR);
        details.setLayout(new javax.swing.BoxLayout(
                details,
                javax.swing.BoxLayout.Y_AXIS
        ));
        details.setBorder(BorderFactory.createEmptyBorder(18, 18, 12, 18));

        JLabel name = new JLabel(brand + " " + model);
        name.setFont(new Font("SansSerif", Font.BOLD, 20));
        name.setForeground(DARK_TEXT);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bikeType = new JLabel(type);
        bikeType.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bikeType.setForeground(PRIMARY_GREEN);
        bikeType.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel serial = new JLabel("Sorozatszám: " + serialNumber);
        serial.setFont(new Font("SansSerif", Font.PLAIN, 12));
        serial.setForeground(SECONDARY_TEXT);
        serial.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(name);
        details.add(javax.swing.Box.createVerticalStrut(7));
        details.add(bikeType);
        details.add(javax.swing.Box.createVerticalStrut(12));
        details.add(serial);

        // Action buttons
        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );
        buttons.setBackground(CARD_COLOR);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        StyledButton editButton = new StyledButton(
                "Módosítás",
                new Color(235, 241, 237),
                PRIMARY_GREEN
        );

        StyledButton deleteButton = new StyledButton(
                "Törlés",
                new Color(250, 235, 235),
                DANGER_COLOR
        );

        editButton.addActionListener(e -> onEdit.run());
        deleteButton.addActionListener(e -> onDelete.run());

        buttons.add(editButton);
        buttons.add(deleteButton);

        add(imageContainer, BorderLayout.NORTH);
        add(details, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}