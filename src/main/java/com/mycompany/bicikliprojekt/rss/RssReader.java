/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.rss;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.net.URLConnection;

/**
 *
 * @author Denes
 */
public class RssReader {
    public static List<RssItem> fetchRssNews(String feedUrl) {
        List<RssItem> newsList = new ArrayList<>();

        try {
            // prepare XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            URLConnection connection = new URL(feedUrl).openConnection();
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Pragma", "no-cache");

            Document doc;
            try (InputStream input = connection.getInputStream()) {
                doc = builder.parse(input);
            }

            doc.getDocumentElement().normalize();

            // get rss items
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                Element element = (Element) itemList.item(i);

                String title = getElementValue(element, "title");
                String link = getElementValue(element, "link");
                String description = getElementValue(element, "description");

                newsList.add(new RssItem(title, link, description));
            }

        } catch (Exception e) {
            System.err.println("There was an issue while reading rss: " + e.getMessage());
        }

        return newsList;
    }

    // helper method 
    private static String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0 && nodeList.item(0) != null) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
