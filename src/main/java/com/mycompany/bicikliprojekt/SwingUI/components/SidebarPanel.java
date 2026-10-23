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

        JLabel logoIcon = new JLabel("🚲");
        logoIcon.setFont(new Font("SansSerif", Font.PLAIN, 36));

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
                "♙", "Profil", true, profileAction
        );
        forSaleButton = createNavigationButton(
                "⚿", "Eladó kerékpárok", false, forSaleAction
        );
        stolenButton = createNavigationButton(
                "⬡", "Ellopott kerékpárok", false, stolenAction
        );
        newsButton = createNavigationButton(
                "▤", "Hírek", false, newsAction
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
            String icon,
            String text,
            boolean selected,
            ActionListener action) {

        JButton button = new JButton(
                "<html><span style='font-size:16px'>"
                + icon + "</span>&nbsp;&nbsp;&nbsp;"
                + text + "</html>"
        );

        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setForeground(DARK_TEXT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.LEADING);
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
}