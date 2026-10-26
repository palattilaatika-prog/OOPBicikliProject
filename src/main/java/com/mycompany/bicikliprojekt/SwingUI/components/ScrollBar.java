/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JButton;
/**
 *
 * @author mcsab
 */
public class ScrollBar extends BasicScrollBarUI {

    private static final Color TRACK_COLOR =
            new Color(245, 248, 246);

    private static final Color THUMB_COLOR =
            new Color(190, 205, 197);

    private static final Color THUMB_HOVER_COLOR =
            new Color(135, 165, 150);
    // Set the scrollbar track and thumb colors.
    @Override
    protected void configureScrollBarColors() {
        trackColor = TRACK_COLOR;
        thumbColor = THUMB_COLOR;
    }
    // Set the minimum size of the scrollbar thumb.
    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(8, 35);
    }
    // Set the maximum size of the scrollbar thumb.
    @Override
    protected Dimension getMaximumThumbSize() {
        return new Dimension(8, Integer.MAX_VALUE);
    }
    // Paint the scrollbar track.
    @Override
    protected void paintTrack(
            Graphics g, javax.swing.JComponent c,
            java.awt.Rectangle trackBounds) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setColor(TRACK_COLOR);
        g2.fillRect(
                trackBounds.x, trackBounds.y,
                trackBounds.width, trackBounds.height
        );
        g2.dispose();
    }
    // Paint the scrollbar thumb with rounded corners.
    @Override
    protected void paintThumb(
            Graphics g, javax.swing.JComponent c,
            java.awt.Rectangle thumbBounds) {

        if (thumbBounds.isEmpty()
                || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        boolean vertical =
                scrollbar.getOrientation()
                == JScrollBar.VERTICAL;

        int thickness = 8;
        int x = vertical
                ? thumbBounds.x + (thumbBounds.width - thickness) / 2
                : thumbBounds.x;

        int y = vertical
                ? thumbBounds.y
                : thumbBounds.y + (thumbBounds.height - thickness) / 2;

        int width = vertical ? thickness : thumbBounds.width;
        int height = vertical ? thumbBounds.height : thickness;

        g2.setColor(THUMB_HOVER_COLOR);
        g2.fillRoundRect(x, y, width, height, 8, 8);
        g2.dispose();
    }
    // Hide the scrollbar's decrease button.
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return invisibleButton();
    }
    // Hide the scrollbar's increase button.
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return invisibleButton();
    }
    // Create a button hidden from the scrollbar.
    private JButton invisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}
