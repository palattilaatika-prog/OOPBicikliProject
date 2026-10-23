/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import com.mycompany.bicikliprojekt.DataBase.DataBaseManager;
import com.mycompany.bicikliprojekt.model.User;
import com.mycompany.bicikliprojekt.services.SessionManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;

/**
 *
 * @author mcsab
 */
public class LoginDialog extends JDialog {

    private final DataBaseManager databaseManager = new DataBaseManager();

    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    private boolean loggedIn = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Bike Manager – Bejelentkezés", true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.add(new JLabel("E-mail-cím:"));
        fieldsPanel.add(emailField);
        fieldsPanel.add(new JLabel("Jelszó:"));
        fieldsPanel.add(passwordField);

        JButton loginButton = new JButton("Bejelentkezés");
        JButton registerButton = new JButton("Regisztráció");

        stylePrimaryButton(loginButton);
        styleSecondaryButton(registerButton);

        loginButton.setPreferredSize(new java.awt.Dimension(145, 42));
        registerButton.setPreferredSize(new java.awt.Dimension(130, 42));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 15, 15, 15)
        );
        buttonsPanel.setLayout(new java.awt.FlowLayout(
                java.awt.FlowLayout.CENTER, 10, 5
        ));

        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());

        getRootPane().setDefaultButton(loginButton);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean showDialog() {
        setVisible(true);
        return loggedIn;
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            showError("Add meg az e-mail-címedet és a jelszavadat!");
            return;
        }

        User user = databaseManager.authenticate(email, password);

        if (user == null) {
            showError("Hibás e-mail-cím vagy jelszó!");
            return;
        }

        SessionManager.setCurrentUser(user);
        loggedIn = true;
        dispose();
    }

    private void register() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            showError("Add meg az e-mail-címedet és a jelszavadat!");
            return;
        }

        String name = JOptionPane.showInputDialog(
                this,
                "Add meg a nevedet:",
                "Regisztráció",
                JOptionPane.QUESTION_MESSAGE
        );

        if (name == null) {
            return;
        }

        name = name.trim();

        if (name.isEmpty()) {
            showError("A név megadása kötelező!");
            return;
        }

        if (password.length() < 8) {
            showError("A jelszónak legalább 8 karakter hosszúnak kell lennie!");
            return;
        }

        try {
            User newUser = new User(1, name, email, password);

            if (databaseManager.register(newUser)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Sikeres regisztráció! Most már bejelentkezhetsz.",
                        "Regisztráció",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showError(
                        "A regisztráció nem sikerült. "
                        + "Lehet, hogy ez az e-mail-cím már használatban van."
                );
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Figyelmeztetés",
                JOptionPane.WARNING_MESSAGE
        );
    }
    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(31, 125, 88));
        button.setForeground(Color.WHITE);
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(232, 241, 236));
        button.setForeground(new Color(31, 125, 88));
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
}
