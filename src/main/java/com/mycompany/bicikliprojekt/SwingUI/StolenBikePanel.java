/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import com.mycompany.bicikliprojekt.DataBase.DataBaseManager;
import com.mycompany.bicikliprojekt.services.SessionManager;
import com.mycompany.bicikliprojekt.model.Bike;
import com.mycompany.bicikliprojekt.model.StolenBike;
import com.mycompany.bicikliprojekt.model.User;
import com.mycompany.bicikliprojekt.SwingUI.components.StyledButton;
import com.mycompany.bicikliprojekt.SwingUI.components.RoundedPanel;
import com.mycompany.bicikliprojekt.SwingUI.components.BikeIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author mcsab
 */
public class StolenBikePanel extends JPanel {

    private final DataBaseManager databaseManager = new DataBaseManager();

    private final JTextField searchField = new JTextField(25);
    private final JPanel cardsPanel = new JPanel(new GridLayout(0, 2, 16, 16));
    private final JLabel resultCountLabel = new JLabel();

    private List<StolenBike> stolenBikes = new ArrayList<>();

    public StolenBikePanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(24, 28, 24, 28));
        setBackground(new Color(245, 247, 250));

        // Fejléc
        JPanel headerPanel = new JPanel(new BorderLayout(12, 12));
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Ellopott kerékpárok");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));

        JLabel subtitle = new JLabel(
                "Keresd meg a bejelentett kerékpárokat, vagy jelents be egy újat."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(95, 105, 120));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitle);

        JButton reportButton = new StyledButton(
                "+ Saját kerékpár bejelentése",
                new Color(38, 132, 91),
                Color.WHITE
        );
        reportButton.addActionListener(e -> openReportDialog());

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(reportButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Keresősáv
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(8, 0, 8, 0));

        searchField.setToolTipText("Keresés márka, modell vagy vázszám alapján");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JButton searchButton = new StyledButton(
                "Keresés",
                new Color(235, 242, 237),
                new Color(35, 125, 85)
        );
        searchButton.addActionListener(e -> refreshCards());

        searchPanel.add(new JLabel("Keresés:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        searchField.addActionListener(e -> refreshCards());

        // Kártyák
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(new EmptyBorder(8, 2, 8, 2));

        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setOpaque(false);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        resultCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        resultCountLabel.setForeground(new Color(95, 105, 120));
        centerPanel.add(resultCountLabel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        loadStolenBikes();
    }

    private void loadStolenBikes() {
        try {
            List<StolenBike> result = databaseManager.getAllLopottBicikli();

            stolenBikes = result == null
                    ? new ArrayList<>()
                    : new ArrayList<>(result);

            refreshCards();
        } catch (RuntimeException ex) {
            ex.printStackTrace();

            Throwable cause = ex.getCause();
            String errorMessage = ex.getMessage();

            if (cause != null) {
                errorMessage += "\nRészletek: " + cause.getMessage();
            }

            JOptionPane.showMessageDialog(
                this,
                "Betöltési hiba"
                        + errorMessage,
                "Mentési hiba",
                JOptionPane.ERROR_MESSAGE
        );
        }
    }

    private void refreshCards() {
        cardsPanel.removeAll();

        String query = searchField.getText()
                .trim()
                .toLowerCase(Locale.ROOT);

        int count = 0;

        for (StolenBike bike : stolenBikes) {
            if (bike == null) {
                continue;
            }

            String brand = safe(bike.getBrand());
            String model = safe(bike.getModel());
            String serial = safe(bike.getSerialNumber());
            String date = safe(bike.getReportedDate());
            String source = safe(bike.getSource());

            String searchable = (
                    brand + " " + model + " " + serial
                    + " " + date + " " + source
            ).toLowerCase(Locale.ROOT);

            if (!searchable.contains(query)) {
                continue;
            }

            cardsPanel.add(createBikeCard(bike));
            count++;
        }

        if (count == 0) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
            emptyPanel.setBorder(new EmptyBorder(35, 15, 35, 15));

            String message = stolenBikes.isEmpty()
                    ? "Jelenleg nincs bejelentett lopott kerékpár."
                    : "Nincs a keresésnek megfelelő kerékpár.";

            JLabel emptyLabel = new JLabel(message);
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            emptyPanel.add(Box.createVerticalGlue());
            emptyPanel.add(emptyLabel);
            emptyPanel.add(Box.createVerticalGlue());

            cardsPanel.setLayout(new BorderLayout());
            cardsPanel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            cardsPanel.setLayout(new GridLayout(0, 2, 16, 16));
        }

        resultCountLabel.setText("Találatok: " + count);

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    
    private boolean isReporter(StolenBike bike) {
        User currentUser = SessionManager.getCurrentUser();

        return currentUser != null
                && currentUser.getUId() == bike.getReporterUserId();
    }

    private JPanel createBikeCard(StolenBike bike) {
        JPanel card = new RoundedPanel(18);
        card.setLayout(new BorderLayout(10, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(16, 16, 16, 16));
        // Ikon: egyelőre nincs képmező a meglévő StolenBike modellben.
        JLabel icon = new JLabel(new BikeIcon(75, 75));
        icon.setPreferredSize(new Dimension(75, 75));

        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setOpaque(false);
        iconPanel.add(icon, BorderLayout.CENTER);
        iconPanel.setPreferredSize(new Dimension(75, 75));

        JLabel name = new JLabel(
                safe(bike.getBrand()) + " " + safe(bike.getModel())
        );
        name.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel serial = new JLabel(
                "Vázszám: " + safe(bike.getSerialNumber())
        );
        serial.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel date = new JLabel(
                "Bejelentve: " + safe(bike.getReportedDate())
        );
        User reporter = databaseManager.getUserById(bike.getReporterUserId());

        String phoneNumber = reporter == null
                || reporter.getPhoneNumber() == null
                || reporter.getPhoneNumber().isBlank()
                ? "Nincs megadva"
                : reporter.getPhoneNumber();

        JLabel phone = new JLabel("Bejelentő telefonszáma: " + phoneNumber);
        phone.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        date.setFont(new Font("SansSerif", Font.PLAIN, 12));
        date.setForeground(new Color(95, 105, 120));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.add(name);
        details.add(Box.createVerticalStrut(9));
        details.add(serial);
        details.add(Box.createVerticalStrut(5));
        details.add(date);
        details.add(Box.createVerticalStrut(5));
        details.add(phone);

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setOpaque(false);
        top.add(iconPanel, BorderLayout.WEST);
        top.add(details, BorderLayout.CENTER);

        JButton detailsButton = new StyledButton(
                "Részletek",
                new Color(235, 242, 237),
                new Color(35, 125, 85)
        );
        detailsButton.addActionListener(e -> showBikeDetails(bike));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottom.setOpaque(false);
        bottom.add(detailsButton);
        
        if (isReporter(bike)) {
            JButton deleteButton = new StyledButton(
                    "Saját bejelentés törlése",
                    new Color(250, 235, 235),
                    new Color(200, 55, 55)
            );
            deleteButton.addActionListener(e -> deleteStolenBike(bike));
            bottom.add(deleteButton);
        }

        card.add(top, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    private void showBikeDetails(StolenBike bike) {
        String message =
                "Márka: " + safe(bike.getBrand()) + "\n"
                + "Modell: " + safe(bike.getModel()) + "\n"
                + "Vázszám: " + safe(bike.getSerialNumber()) + "\n"
                + "Bejelentés dátuma: " + safe(bike.getReportedDate()) + "\n"
                + "Bejelentés forrása: " + safe(bike.getSource());

        JOptionPane.showMessageDialog(
                this,
                message,
                "Kerékpár részletei",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteStolenBike(StolenBike bike) {
        if (!isReporter(bike)) {
            showError("Csak a saját bejelentésedet törölheted.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Biztosan törlöd a saját bejelentésedet?",
                "Bejelentés törlése",
                JOptionPane.YES_NO_OPTION
        );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        User currentUser = SessionManager.getCurrentUser();

        databaseManager.deleteLopottBicikliById(
                bike.getBicikliid(),
                currentUser.getUId()
        );

        loadStolenBikes();
    }

    private void openReportDialog() {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser == null) {
            showError("A bejelentéshez először be kell jelentkezned!");
            return;
        }

        
        final List<Bike> userBikes;

        try {
            List<Bike> loadedBikes = databaseManager.getBikesByUserId(
                    currentUser.getUId()
            );

            userBikes = loadedBikes == null
                    ? new ArrayList<>()
                    : new ArrayList<>(loadedBikes);

        } catch (RuntimeException ex) {
            showError("Nem sikerült betölteni a saját kerékpárjaidat.");
            return;
        }

        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Lopott kerékpár bejelentése",
                Dialog.ModalityType.APPLICATION_MODAL
        );

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel form = new RoundedPanel(18);
        form.setLayout(new GridBagLayout());
        form.setBorder(new EmptyBorder(22, 24, 22, 24));
        form.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 6, 7, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JComboBox<String> bikeSelector = new JComboBox<>();
        for (Bike bike : userBikes) {
            bikeSelector.addItem(
                    safe(bike.getBrand()) + " " + safe(bike.getModel())
                    + " | " + safe(bike.getSerialNumber())
            );
        }

        JRadioButton ownBikeButton =
                new JRadioButton("Saját kerékpár", !userBikes.isEmpty());
        JRadioButton manualBikeButton =
                new JRadioButton("Másik kerékpár kézi megadása",
                        userBikes.isEmpty());

        ownBikeButton.setBackground(Color.WHITE);
        manualBikeButton.setBackground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(ownBikeButton);
        group.add(manualBikeButton);

        JTextField brandField = new JTextField(22);
        JTextField modelField = new JTextField(22);
        JTextField serialField = new JTextField(22);
        JTextField dateField = new JTextField(LocalDate.now().toString(), 22);
        JTextField sourceField = new JTextField(22);
        
        Color fieldBorderColor = new Color(215, 225, 219);

        JTextField[] dialogFields = {
            brandField, modelField, serialField, dateField, sourceField
        };

        for (JTextField field : dialogFields) {
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
            field.setForeground(new Color(35, 50, 60));
            field.setBackground(Color.WHITE);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(fieldBorderColor),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }

        bikeSelector.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bikeSelector.setBackground(Color.WHITE);
        bikeSelector.setBorder(
                BorderFactory.createLineBorder(fieldBorderColor)
        );

        ownBikeButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        manualBikeButton.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel formTitle = new JLabel("Add meg a kerékpár adatait");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        formTitle.setForeground(new Color(35, 45, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 6, 12, 6);
        form.add(formTitle, gbc);

        gbc.insets = new Insets(7, 6, 7, 6);
        int row = 1;

        addFormRow(form, gbc, row++, "Kerékpár kiválasztása:", bikeSelector);
        addFormRow(form, gbc, row++, "", ownBikeButton);
        addFormRow(form, gbc, row++, "", manualBikeButton);
        addFormRow(form, gbc, row++, "Márka:", brandField);
        addFormRow(form, gbc, row++, "Modell:", modelField);
        addFormRow(form, gbc, row++, "Vázszám:", serialField);
        addFormRow(form, gbc, row++, "Dátum (ÉÉÉÉ-HH-NN):", dateField);
        addFormRow(form, gbc, row++, "Bejelentés forrása:", sourceField);

        Runnable fillSelectedBike = () -> {
            if (!ownBikeButton.isSelected()) {
                return;
            }

            int index = bikeSelector.getSelectedIndex();

            if (index >= 0 && index < userBikes.size()) {
                Bike bike = userBikes.get(index);
                brandField.setText(safe(bike.getBrand()));
                modelField.setText(safe(bike.getModel()));
                serialField.setText(safe(bike.getSerialNumber()));
            }
        };

        Runnable updateMode = () -> {
            boolean manual = manualBikeButton.isSelected();

            bikeSelector.setEnabled(!manual && !userBikes.isEmpty());
            brandField.setEditable(manual);
            modelField.setEditable(manual);
            serialField.setEditable(manual);

            if (manual) {
                brandField.setText("");
                modelField.setText("");
                serialField.setText("");
            } else {
                fillSelectedBike.run();
            }
        };

        bikeSelector.addActionListener(e -> fillSelectedBike.run());
        ownBikeButton.addActionListener(e -> updateMode.run());
        manualBikeButton.addActionListener(e -> updateMode.run());

        if (userBikes.isEmpty()) {
            ownBikeButton.setEnabled(false);
            bikeSelector.setEnabled(false);
            manualBikeButton.setSelected(true);
        } else {
            fillSelectedBike.run();
        }

        updateMode.run();

        JButton cancelButton = new StyledButton(
                "Mégse",
                new Color(235, 242, 237),
                new Color(35, 125, 85)
        );
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton submitButton = new StyledButton(
                "Bejelentés elküldése",
                new Color(38, 132, 91),
                Color.WHITE
        );
        submitButton.addActionListener(e -> {
            String brand = brandField.getText().trim();
            String model = modelField.getText().trim();
            String serial = serialField.getText().trim();
            String date = dateField.getText().trim();
            String source = sourceField.getText().trim();

            if (brand.isEmpty() || model.isEmpty()
                    || serial.isEmpty() || date.isEmpty()
                    || source.isEmpty()) {
                showError("Kérlek, tölts ki minden mezőt!");
                return;
            }

            LocalDate parsedDate;

            try {
                parsedDate = LocalDate.parse(date);
            } catch (DateTimeParseException ex) {
                showError("Hibás dátum. Példa: 2026-10-24");
                return;
            }

            if (parsedDate.isAfter(LocalDate.now())) {
                showError("A bejelentés dátuma nem lehet jövőbeli!");
                return;
            }

            for (StolenBike existing : stolenBikes) {
                if (existing.getSerialNumber() != null
                        && existing.getSerialNumber().trim()
                                .equalsIgnoreCase(serial)) {
                    showError(
                            "Ez a vázszám már szerepel a bejelentések között!"
                    );
                    return;
                }
            }

            int answer = JOptionPane.showConfirmDialog(
                    dialog,
                    "Biztosan bejelented ezt a kerékpárt lopottként?\n\n"
                    + brand + " " + model + "\nVázszám: " + serial,
                    "Bejelentés megerősítése",
                    JOptionPane.YES_NO_OPTION
            );

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                StolenBike stolenBike = new StolenBike(
                        1,
                        currentUser.getUId(),
                        brand, 
                        model, 
                        serial,
                        date,
                        source
                );

                databaseManager.insertLopottBicikli(stolenBike);

                JOptionPane.showMessageDialog(
                        dialog,
                        "A bejelentést elküldtük.",
                        "Sikeres bejelentés",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();
                loadStolenBikes();

            } catch (RuntimeException ex) {
                showError("Nem sikerült elmenteni a bejelentést.");
            }
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        buttons.add(cancelButton);
        buttons.add(submitButton);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        JScrollPane formScrollPane = new JScrollPane(form);
        formScrollPane.setBorder(BorderFactory.createEmptyBorder());
        formScrollPane.getViewport().setBackground(new Color(245, 247, 250));
        formScrollPane.setBackground(new Color(245, 247, 250));

        dialog.setContentPane(formScrollPane);
        dialog.setSize(620, 580);
        dialog.setMinimumSize(new Dimension(580, 540));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent component
    ) {
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;

        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Hiba",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
