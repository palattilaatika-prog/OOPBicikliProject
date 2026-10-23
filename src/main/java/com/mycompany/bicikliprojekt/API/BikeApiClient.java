/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.API;
import com.google.gson.Gson;
import com.mycompany.bicikliprojekt.model.LopottBicikli;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Denes
 */
public class BikeApiClient {
    private static final String API_URL = "https://bikeindex.org/api/v3/search?stolenness=stolen&per_page=10";

    public static List<LopottBicikli> fetchStolenBikesFromApi() {
        List<LopottBicikli> stolenBikes = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.err.println("HTTP Hiba: " + conn.getResponseCode());
                return stolenBikes;
            }

            // Reading json
            try (Reader reader = new InputStreamReader(conn.getInputStream())) {
                Gson gson = new Gson();
                BikeApiResponse response = gson.fromJson(reader, BikeApiResponse.class);

                if (response != null && response.getBikes() != null) {
                    for (BikeApiItem item : response.getBikes()) {
                        
                        int id = item.getId();
                        String brand = item.getManufacturerName() != null ? item.getManufacturerName() : "UNKNOWN";
                        String model = item.getFrameModel() != null ? item.getFrameModel() : "UNKNOWN";
                        String serialNumber = item.getSerial() != null ? item.getSerial() : "UNKNOWN";

                        // serial number validation
                        if (!serialNumber.matches("^[a-zA-Z0-9]+$")) {
                            serialNumber = "NO" + id + "SERIAL";
                        }

                        // converting date
                        String reportedDate = "UNKNOWN";
                        if (item.getDateStolen() != null && item.getDateStolen() > 0) {
                            reportedDate = Instant.ofEpochSecond(item.getDateStolen())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                    .format(DateTimeFormatter.ISO_LOCAL_DATE);
                        }

                        // creating new StolenBikes
                        LopottBicikli stolenBike = new LopottBicikli(
                                id,
                                brand,
                                model,
                                serialNumber,
                                reportedDate,
                                "API"
                        );

                        stolenBikes.add(stolenBike);
                    }
                }
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Error while processing api: " + e.getMessage());
        }

        return stolenBikes;
    }
}
