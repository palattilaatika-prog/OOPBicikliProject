/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.Icon;

/**
 *
 * @author mcsab
 */
// Rajzolt alapértelmezett kerékpárikon.
    public class BikeIcon implements Icon {
        private final int width;
        private final int height;

        public BikeIcon(int width, int height) {
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
            double scale = Math.min(width / 180.0, height / 125.0);
            g.translate(
                    (width - 180 * scale) / 2.0,
                    (height - 125 * scale) / 2.0
            );
            g.scale(scale, scale);

            g.setColor(new Color(232, 239, 234));
            g.fillRoundRect(0, 0, 180, 125, 18, 18);
            
            g.setColor(new Color(52, 75, 82));
            g.setStroke(new BasicStroke(
                3.2f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
            ));

            int leftX = 28;
            int rightX = 152;
            int wheelY = 91;
            int radius = 19;
            int crankX = 91;
            int crankY = 85;
            int seatX = 70;
            int seatY = 48;
            int handleX = 119;
            int handleY = 50;


            g.drawOval(leftX - radius, wheelY - radius, radius * 2, radius * 2);
            g.drawOval(rightX - radius, wheelY - radius, radius * 2, radius * 2);

            g.drawLine(leftX, wheelY, crankX, crankY);
            g.drawLine(crankX, crankY, rightX, wheelY);
            g.drawLine(leftX, wheelY, seatX, seatY);
            g.drawLine(seatX, seatY, crankX, crankY);
            g.drawLine(seatX, seatY, handleX, handleY);
            g.drawLine(handleX, handleY, rightX, wheelY);

            g.drawLine(seatX - 9, seatY, seatX + 10, seatY);
            g.drawLine(handleX - 5, handleY - 5, handleX + 7, handleY + 2);
            g.drawLine(handleX - 5, handleY - 5, handleX - 10, handleY - 12);

            g.dispose();
    }
}

