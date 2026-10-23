package com.mycompany.bicikliprojekt.SwingUI;


import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mycompany.bicikliprojekt.SwingUI.components.ContentPanel;
import com.mycompany.bicikliprojekt.SwingUI.components.SidebarPanel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mcsab
 */
public class MainWindow extends JFrame{
    private final ContentPanel contentPanel;
    private final SidebarPanel sidebarPanel;

    public MainWindow() {
        setTitle("Bike Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setMinimumSize(new java.awt.Dimension(1000, 650));
        setLocationRelativeTo(null);

        contentPanel = new ContentPanel();
        sidebarPanel = new SidebarPanel(
                e -> showPage("Profil"),
                e -> showPage("Eladó kerékpárok"),
                e -> showPage("Ellopott kerékpárok"),
                e -> showPage("Hírek")
        );

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(sidebarPanel, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);

        setContentPane(root);
        showPage("Profil");
    }

    private void showPage(String pageName) {
        contentPanel.showPage(pageName);
        sidebarPanel.setActivePage(pageName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
