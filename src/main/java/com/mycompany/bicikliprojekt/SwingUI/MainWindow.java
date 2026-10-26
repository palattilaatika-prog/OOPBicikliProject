package com.mycompany.bicikliprojekt.SwingUI;


import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.mycompany.bicikliprojekt.DataBase.DataBase;
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
    // Build the main window and connect the navigation pages.
    public MainWindow() {
        DataBase.createTables();
        
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
    // Display the selected page and update the sidebar.
    private void showPage(String pageName) {
        contentPanel.showPage(pageName);
        sidebarPanel.setActivePage(pageName);
    }
    // Set the colors and fonts used by Swing dialogs.
    private static void applyDialogTheme() {
        Color background = new Color(248, 250, 249);
        Color paleGreen = new Color(232, 241, 236);
        Color green = new Color(31, 125, 88);
        Color text = new Color(35, 50, 60);

        java.awt.Font normalFont =
                new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13);
        java.awt.Font boldFont =
                new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13);

        UIManager.put("OptionPane.background", background);
        UIManager.put("OptionPane.messageForeground", text);
        UIManager.put("OptionPane.messageFont", normalFont);
        UIManager.put("OptionPane.buttonFont", boldFont);
        UIManager.put("OptionPane.border",
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new Color(220, 228, 223)
                        ),
                        javax.swing.BorderFactory.createEmptyBorder(
                                12, 14, 10, 14
                        )
                )
        );
        UIManager.put("OptionPane.buttonAreaBorder",
                javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0)
        );

        UIManager.put("Panel.background", background);
        UIManager.put("Button.background", paleGreen);
        UIManager.put("Button.foreground", green);
        UIManager.put("Button.font", boldFont);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", text);
        UIManager.put("TextField.caretForeground", green);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", text);

        UIManager.put("FileChooser.background", background);
        UIManager.put("FileChooser.foreground", text);
        UIManager.put("FileChooser.listViewBackground", Color.WHITE);
    }
}
