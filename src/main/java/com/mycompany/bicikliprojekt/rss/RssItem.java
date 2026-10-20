/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.rss;

/**
 *
 * @author Denes
 */
    public class RssItem {
    private String title;
    private String link;
    private String description;

    public RssItem(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return title; // Visible in swing UI
    }
}

