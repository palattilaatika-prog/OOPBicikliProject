/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.services;
import com.mycompany.bicikliprojekt.API.BikeApiClient;
import com.mycompany.bicikliprojekt.DataBase.DataBaseManager;
import com.mycompany.bicikliprojekt.model.StolenBike;

import java.util.List;
/**
 *
 * @author Denes
 */
public class StolenBikeService {
    private final DataBaseManager dbManager;

    public StolenBikeService() {
        this.dbManager = new DataBaseManager();
    }

    //downloads the latest stolen bikes from the API and saves in sqlite
    public void syncStolenBikesFromApi() {
        System.out.println("API adatok frissítése indult...");
        
        //downloading from api
        List<StolenBike> apiBikes = BikeApiClient.fetchStolenBikesFromApi();

        if (!apiBikes.isEmpty()) {
            // saving in db
            dbManager.insertAllLopottBicikli(apiBikes);
            System.out.println("Sikeresen elmentve " + apiBikes.size() + " db ellopott kerékpár az API-ból.");
        } else {
            System.err.println("Nem sikerült adatokat letölteni az API-ból.");
        }
    }
}
