/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

/**
 *
 * @author mcsab
 */
public class SidebarPanel extends JPanel {

    private static final Color SIDEBAR_BACKGROUND =
            new Color(248, 250, 249);
    private static final Color DARK_TEXT =
            new Color(35, 50, 60);
    private static final Color PRIMARY_GREEN =
            new Color(35, 125, 85);
    private static final Color SELECTED_BACKGROUND =
            new Color(228, 237, 231);
    private static final Color SECONDARY_TEXT =
            new Color(105, 120, 128);

    private final JButton profileButton;
    private final JButton forSaleButton;
    private final JButton stolenButton;
    private final JButton newsButton;

    public SidebarPanel(
            ActionListener profileAction,
            ActionListener forSaleAction,
            ActionListener stolenAction,
            ActionListener newsAction) {

        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BACKGROUND);
        setPreferredSize(new Dimension(260, 0));
        setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1, new Color(225, 231, 228)
        ));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(
                28, 16, 20, 16
        ));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(
                logoPanel, BoxLayout.X_AXIS
        ));
        logoPanel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel logoIcon = new JLabel(new BikeIcon(56, 39));

        JLabel logoText = new JLabel(
                "<html><b>Bike</b><br><font color='#237D55'>Manager</font></html>"
        );
        logoText.setFont(new Font("SansSerif", Font.BOLD, 23));
        logoText.setForeground(DARK_TEXT);

        logoPanel.add(logoIcon);
        logoPanel.add(Box.createHorizontalStrut(12));
        logoPanel.add(logoText);

        content.add(logoPanel);
        content.add(Box.createVerticalStrut(42));

        profileButton = createNavigationButton(
                new MenuIcon(MenuIconType.PROFILE), "Profil", true, profileAction
        );
        forSaleButton = createNavigationButton(
                new MenuIcon(MenuIconType.SALE), "Eladó kerékpárok", false, forSaleAction
        );
        stolenButton = createNavigationButton(
                new MenuIcon(MenuIconType.STOLEN), "Ellopott kerékpárok", false, stolenAction
        );
        newsButton = createNavigationButton(
                new MenuIcon(MenuIconType.NEWS), "Hírek", false, newsAction
        );

        content.add(profileButton);
        content.add(Box.createVerticalStrut(8));
        content.add(forSaleButton);
        content.add(Box.createVerticalStrut(8));
        content.add(stolenButton);
        content.add(Box.createVerticalStrut(8));
        content.add(newsButton);

        add(content, BorderLayout.NORTH);

        JLabel footer = new JLabel("Bike Manager");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setForeground(SECONDARY_TEXT);
        footer.setBorder(BorderFactory.createEmptyBorder(
                12, 24, 20, 12
        ));

        add(footer, BorderLayout.SOUTH);
    }

    private JButton createNavigationButton(
            Icon icon,
            String text,
            boolean selected,
            ActionListener action) {

            JButton button = new JButton(text, icon);

            button.setFont(new Font("SansSerif", Font.PLAIN, 15));
            button.setForeground(DARK_TEXT);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setHorizontalTextPosition(SwingConstants.TRAILING);
            button.setIconTextGap(16);
            button.setMargin(new Insets(0, 14, 0, 8));
            button.setPreferredSize(new Dimension(226, 54));
            button.setMinimumSize(new Dimension(226, 54));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
            button.setAlignmentX(LEFT_ALIGNMENT);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBackground(
                    selected ? SELECTED_BACKGROUND : SIDEBAR_BACKGROUND
            );
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (action != null) {
                button.addActionListener(action);
            }

            return button;
        }
    public void setActivePage(String page) {
        setButtonSelected(profileButton, "Profil".equals(page));
        setButtonSelected(forSaleButton, "Eladó kerékpárok".equals(page));
        setButtonSelected(stolenButton, "Ellopott kerékpárok".equals(page));
        setButtonSelected(newsButton, "Hírek".equals(page));
    }

    private void setButtonSelected(
            JButton button, boolean selected) {

        button.setBackground(
                selected ? SELECTED_BACKGROUND : SIDEBAR_BACKGROUND
        );
        button.setForeground(
                selected ? PRIMARY_GREEN : DARK_TEXT
        );
    }
    private enum MenuIconType {
    PROFILE, SALE, STOLEN, NEWS
}

    private static class MenuIcon implements Icon {
        private final MenuIconType type;

        MenuIcon(MenuIconType type) {
            this.type = type;
        }

        @Override
        public int getIconWidth() {
            return 24;
        }

        @Override
        public int getIconHeight() {
            return 24;
        }

        @Override
        public void paintIcon(
                java.awt.Component component,
                Graphics graphics,
                int x,
                int y) {

            Graphics2D g = (Graphics2D) graphics.create();
            g.translate(x, y);
            g.setColor(new Color(35, 65, 70));
            g.setStroke(new BasicStroke(
                    1.8f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            switch (type) {
                case PROFILE:
                    g.drawOval(8, 2, 8, 8);
                    g.drawArc(4, 11, 16, 12, 0, 180);
                    break;

                case SALE:
                    int[] saleX = {4, 13, 21, 21, 11};
                    int[] saleY = {4, 4, 12, 20, 20};
                    g.drawPolygon(saleX, saleY, saleX.length);
                    g.drawOval(7, 7, 3, 3);
                    break;

                case STOLEN:
                    g.drawOval(1, 13, 8, 8);
                    g.drawOval(15, 13, 8, 8);
                    g.drawLine(5, 17, 11, 9);
                    g.drawLine(11, 9, 17, 17);
                    g.drawLine(5, 17, 13, 17);
                    g.drawLine(13, 17, 11, 9);
                    g.drawLine(11, 9, 18, 9);
                    g.drawLine(18, 9, 19, 13);
                    break;

                case NEWS:
                    g.drawRoundRect(4, 2, 16, 20, 2, 2);
                    g.drawLine(8, 7, 16, 7);
                    g.drawLine(8, 11, 16, 11);
                    g.drawLine(8, 15, 16, 15);
                    g.drawLine(8, 19, 14, 19);
                    break;
            }

            g.dispose();
        }
    }
}