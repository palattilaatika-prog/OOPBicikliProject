/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.SwingUI;

import com.mycompany.bicikliprojekt.SwingUI.components.RoundedPanel;
import com.mycompany.bicikliprojekt.SwingUI.components.ScrollBar;
import com.mycompany.bicikliprojekt.SwingUI.components.StyledButton;
import com.mycompany.bicikliprojekt.rss.RssItem;
import com.mycompany.bicikliprojekt.rss.RssReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;
import java.util.List;
/**
 *
 * @author mcsab
 */
public class NewsPanel extends JPanel {

    private static final String FEED_URL =
            "https://feeds.purplemanager.com/193c804a-a673-47bd-b09b-11baf4822a17/bikeradar-news";

    private final JPanel cardsPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Hírek betöltése...");
    private final JButton refreshButton = new StyledButton(
            "Frissítés",
            new Color(35, 125, 85),
            Color.WHITE
    );
        // Build the news page and start loading the RSS feed.
        public NewsPanel() {
            setLayout(new BorderLayout(18, 18));
            setBorder(new EmptyBorder(24, 28, 24, 28));
            setBackground(new Color(245, 247, 250));

            JPanel heading = new JPanel();
            heading.setOpaque(false);
            heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

            JLabel title = new JLabel("Kerékpáros hírek");
            title.setFont(new Font("SansSerif", Font.BOLD, 27));
            title.setForeground(new Color(35, 45, 40));

            JLabel subtitle = new JLabel("Friss hírek a BikeRadar RSS-csatornájáról.");
            subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
            subtitle.setForeground(new Color(95, 105, 120));

            heading.add(title);
            heading.add(Box.createVerticalStrut(6));
            heading.add(subtitle);

            JPanel header = new JPanel(new BorderLayout(12, 12));
            header.setOpaque(false);
            header.add(heading, BorderLayout.CENTER);
            header.add(refreshButton, BorderLayout.EAST);

            cardsPanel.setOpaque(false);
            cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
            cardsPanel.setBorder(new EmptyBorder(4, 2, 4, 2));

            JScrollPane scrollPane = new JScrollPane(cardsPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.getVerticalScrollBar().setUI(new ScrollBar());
            scrollPane.getVerticalScrollBar().setUnitIncrement(18);

            statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            statusLabel.setForeground(new Color(95, 105, 120));

            JPanel content = new JPanel(new BorderLayout(0, 10));
            content.setOpaque(false);
            content.add(scrollPane, BorderLayout.CENTER);
            content.add(statusLabel, BorderLayout.SOUTH);

            add(header, BorderLayout.NORTH);
            add(content, BorderLayout.CENTER);

            refreshButton.addActionListener(e -> loadNews());
            loadNews();
        }
        // Fetch RSS items in the background and refresh the news cards.
        private void loadNews() {
            refreshButton.setEnabled(false);
            statusLabel.setText("Hírek betöltése...");
            cardsPanel.removeAll();
            cardsPanel.revalidate();
            cardsPanel.repaint();

            new SwingWorker<List<RssItem>, Void>() {
                // Download the RSS items without blocking the UI.
                @Override
                protected List<RssItem> doInBackground() {
                    return RssReader.fetchRssNews(
                            FEED_URL + "?refresh=" + System.currentTimeMillis()
                    );
                }
                // Display the RSS results after the background task finishes.
                @Override
                protected void done() {
                    try {
                        List<RssItem> items = get();

                        if (items == null || items.isEmpty()) {
                            statusLabel.setText(
                                    "Nem érkezett megjeleníthető hír. "
                                    + "A feed üres lehet, vagy a letöltés nem sikerült."
                            );
                            return;
                        }

                        for (RssItem item : items) {
                            if (item != null) {
                                cardsPanel.add(createNewsCard(item));
                                cardsPanel.add(Box.createVerticalStrut(12));
                            }
                        }

                        statusLabel.setText("Betöltött hírek: " + items.size());
                        cardsPanel.revalidate();
                        cardsPanel.repaint();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Nem sikerült betölteni a híreket.");
                    } finally {
                        refreshButton.setEnabled(true);
                    }
                }
            }.execute();
        }
        // Create a card for one news item.
        private JPanel createNewsCard(RssItem item) {
            JPanel card = new RoundedPanel(18);
            card.setLayout(new BorderLayout(0, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(new EmptyBorder(18, 20, 18, 20));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea title = new JTextArea(safe(item.getTitle()));
            title.setFont(new Font("SansSerif", Font.BOLD, 17));
            title.setForeground(new Color(35, 45, 40));
            title.setLineWrap(true);
            title.setWrapStyleWord(true);
            title.setEditable(false);
            title.setFocusable(false);
            title.setOpaque(false);

            String descriptionText = safe(item.getDescription())
                    .replaceAll("<[^>]*>", " ")
                    .replaceAll("\\s+", " ")
                    .trim();

            if (descriptionText.length() > 400) {
                descriptionText = descriptionText.substring(0, 400) + "...";
            }

            JTextArea description = new JTextArea(descriptionText);
            description.setFont(new Font("SansSerif", Font.PLAIN, 13));
            description.setForeground(new Color(95, 105, 120));
            description.setLineWrap(true);
            description.setWrapStyleWord(true);
            description.setEditable(false);
            description.setFocusable(false);
            description.setOpaque(false);

            JButton openButton = new StyledButton(
                    "Cikk megnyitása",
                    new Color(235, 242, 237),
                    new Color(35, 125, 85)
            );
            openButton.addActionListener(e -> openArticle(item.getLink()));
            openButton.setEnabled(item.getLink() != null && !item.getLink().isBlank());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.add(openButton);

            card.add(title, BorderLayout.NORTH);
            card.add(description, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.SOUTH);

            return card;
        }
        // Open the selected article in the browser.
        private void openArticle(String link) {
            try {
                if (link == null || link.isBlank()) {
                    return;
                }
                Desktop.getDesktop().browse(URI.create(link));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "A cikket nem sikerült megnyitni.",
                        "Hiba",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        // Replace a missing text value with an empty string.
        private String safe(String value) {
            return value == null ? "" : value;
        }
    }
