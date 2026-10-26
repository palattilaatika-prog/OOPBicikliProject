/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author mcsab
 */
public class BikeIcon implements Icon {
    private final int width;
    private final int height;
    private final Image image;
    // Load the bike image and set the icon size.
    public BikeIcon(int width, int height) {
        this.width = width;
        this.height = height;

        URL imageUrl = BikeIcon.class.getResource("/icons/bike.jpg");
        this.image = imageUrl == null
                ? null
                : new ImageIcon(imageUrl).getImage();
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
    // Draw the bike image centered and scaled inside the icon.
    @Override
    public void paintIcon(
            Component component,
            Graphics graphics,
            int x,
            int y) {

        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
        );

        if (image != null
                && image.getWidth(component) > 0
                && image.getHeight(component) > 0) {

            double scale = Math.min(
                    (double) width / image.getWidth(component),
                    (double) height / image.getHeight(component)
            );

            int drawWidth = (int) (image.getWidth(component) * scale);
            int drawHeight = (int) (image.getHeight(component) * scale);

            g.drawImage(
                    image,
                    x + (width - drawWidth) / 2,
                    y + (height - drawHeight) / 2,
                    drawWidth,
                    drawHeight,
                    component
            );
        }

        g.dispose();
    }
}
