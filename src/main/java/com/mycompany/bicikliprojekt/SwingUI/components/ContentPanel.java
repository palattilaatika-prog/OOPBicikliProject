/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mycompany.bicikliprojekt.SwingUI.ProfilePanel;
import com.mycompany.bicikliprojekt.SwingUI.ForSalePanel;
import com.mycompany.bicikliprojekt.SwingUI.StolenBikePanel;
import com.mycompany.bicikliprojekt.SwingUI.NewsPanel;

/**
 *
 * @author mcsab
 */
public class ContentPanel extends JPanel {

    private final CardLayout cardLayout;
    private final JPanel pages;

    public ContentPanel() {
        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);

        pages.add(new ProfilePanel(), "Profil");
        pages.add(new ForSalePanel(), "Eladó kerékpárok");
        pages.add(new StolenBikePanel(), "Ellopott kerékpárok");
        pages.add(new NewsPanel(), "Hírek");

        setLayout(new BorderLayout());
        add(pages, BorderLayout.CENTER);
    }

    public void showPage(String pageName) {
        cardLayout.show(pages, pageName);
    }

    private JPanel createPlaceholder(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(
                new JLabel(title, SwingConstants.CENTER),
                BorderLayout.CENTER
        );
        return panel;
    }
}
