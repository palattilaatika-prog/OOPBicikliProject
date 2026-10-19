/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author mcsab
 */
public class ProfilePanel extends JPanel {
    private JTable bikeTable;
    private DefaultTableModel tableModel;
    
    public ProfilePanel(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        createTable();
        createButtons();
        addSampleData();
    }
    
    private void createTable(){
        String[] columns = { "Márka", "Modell", "Típus", "Sorozatszám"};
        
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        bikeTable = new JTable(tableModel);
        bikeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bikeTable.setRowHeight(28);
        bikeTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(bikeTable);

        add(scrollPane, BorderLayout.CENTER);
    }
    private void createButtons() {
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        JButton addButton = new JButton("Hozzáadás");
        JButton editButton = new JButton("Módosítás");
        JButton deleteButton = new JButton("Törlés");

        addButton.addActionListener(e -> addBike());
        editButton.addActionListener(e -> editBike());
        deleteButton.addActionListener(e -> deleteBike());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JLabel title = new JLabel("Saját kerékpárjaim");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{
            "Trek", "Marlin 5", "MTB", "BK001"
        });

        tableModel.addRow(new Object[]{
            "Giant", "Escape 3", "Városi", "BK002"
        });
    }
    private void addBike() {
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField serialField = new JTextField();

        Object[] fields = {
            "Márka:", brandField,
            "Modell:", modelField,
            "Típus:", typeField,
            "Sorozatszám:", serialField
        };

        int result = JOptionPane.showConfirmDialog(
            this,
            fields,
            "Új kerékpár hozzáadása",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String type = typeField.getText().trim();
        String serial = serialField.getText().trim();

        if (brand.isEmpty() || serial.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "A márka és a sorozatszám megadása kötelező!",
                "Hiányzó adat",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (serialExists(serial, -1)) {
            JOptionPane.showMessageDialog(
                this,
                "Ez a sorozatszám már szerepel a listában!",
                "Duplikált sorozatszám",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tableModel.addRow(new Object[]{
            brand, model, type, serial
        });
    }

    private void editBike() {
        int selectedRow = bikeTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Először válassz ki egy kerékpárt!",
                "Nincs kiválasztás",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JTextField brandField = new JTextField(
            tableModel.getValueAt(selectedRow, 0).toString()
        );

        JTextField modelField = new JTextField(
            tableModel.getValueAt(selectedRow, 1).toString()
        );

        JTextField typeField = new JTextField(
            tableModel.getValueAt(selectedRow, 2).toString()
        );

        JTextField serialField = new JTextField(
            tableModel.getValueAt(selectedRow, 3).toString()
        );

        Object[] fields = {
            "Márka:", brandField,
            "Modell:", modelField,
            "Típus:", typeField,
            "Sorozatszám:", serialField
        };

        int result = JOptionPane.showConfirmDialog(
            this,
            fields,
            "Kerékpár módosítása",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String type = typeField.getText().trim();
        String serial = serialField.getText().trim();

        if (brand.isEmpty() || serial.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "A márka és a sorozatszám megadása kötelező!",
                "Hiányzó adat",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (serialExists(serial, selectedRow)) {
            JOptionPane.showMessageDialog(
                this,
                "Ez a sorozatszám már szerepel egy másik kerékpárnál!",
                "Duplikált sorozatszám",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tableModel.setValueAt(brand, selectedRow, 0);
        tableModel.setValueAt(model, selectedRow, 1);
        tableModel.setValueAt(type, selectedRow, 2);
        tableModel.setValueAt(serial, selectedRow, 3);
    }

    private void deleteBike() {
        int selectedRow = bikeTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Először válassz ki egy kerékpárt!",
                "Nincs kiválasztás",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int result = JOptionPane.showConfirmDialog(
            this,
            "Biztosan törölni szeretnéd a kiválasztott kerékpárt?",
            "Törlés megerősítése",
            JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private boolean serialExists(String serial, int excludedRow) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (i == excludedRow) {
                continue;
            }

            String existingSerial =
                tableModel.getValueAt(i, 3).toString();

            if (existingSerial.equalsIgnoreCase(serial)) {
                return true;
            }
        }

        return false;
    }
}
