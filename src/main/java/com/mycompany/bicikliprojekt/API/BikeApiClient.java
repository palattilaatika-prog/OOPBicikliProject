/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.API;
import com.google.gson.Gson;
import com.mycompany.bicikliprojekt.model.StolenBike;
import com.mycompany.bicikliprojekt.API.httphelper.HttpHandler;
import com.mycompany.bicikliprojekt.API.httphelper.HttpResponse;

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

    public static List<StolenBike> fetchStolenBikesFromApi() {
        List<StolenBike> stolenBikes = new ArrayList<>();

        HttpResponse response = HttpHandler.executeGetRequest(API_URL);

        if (!response.isSuccess() || response.getBody() == null) {
            System.err.println("Hiba az API kérés során. HTTP Kód: " + response.getStatusCode());
            return stolenBikes;
        }

        try {
            // json processing
            Gson gson = new Gson();
            BikeApiResponse apiResponse = gson.fromJson(response.getBody(), BikeApiResponse.class);

            if (apiResponse != null && apiResponse.getBikes() != null) {
                for (BikeApiItem item : apiResponse.getBikes()) {

                    int id = item.getId();
                    String brand = item.getManufacturerName() != null ? item.getManufacturerName() : "UNKNOWN";
                    String model = item.getFrameModel() != null ? item.getFrameModel() : "UNKNOWN";
                    String serialNumber = item.getSerial() != null ? item.getSerial() : "UNKNOWN";

                    // validation
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
                    StolenBike stolenBike = new StolenBike(
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
        } catch (Exception e) {
            System.err.println("Error processing api: " + e.getMessage());
        }

        return stolenBikes;
    }
}
