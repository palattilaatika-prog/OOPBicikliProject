package com.mycompany.bicikliprojekt.SwingUI;


import javax.swing.*;
import java.awt.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mcsab
 */
public class MainWindow extends JFrame{
    private JTabbedPane tabbedPane;
    
    public MainWindow(){
        setTitle("Bike Manager");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        createTabs();
    }
    
    private void createTabs(){
        tabbedPane = new JTabbedPane();
        
        
        JPanel forSalePanel = new ForSalePanel();
        JPanel stolenPanel = new JPanel();
        JPanel newsPanel = new JPanel();
        
        stolenPanel.add(new JLabel("Ellopott kerékpárok"));
        newsPanel.add(new JLabel("Kerékpáros hírek"));

        tabbedPane.addTab("Profil", new ProfilePanel());
        tabbedPane.addTab("Eladó kerékpárok", forSalePanel);
        tabbedPane.addTab("Ellopott kerékpárok", stolenPanel);
        tabbedPane.addTab("Hírek", newsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
