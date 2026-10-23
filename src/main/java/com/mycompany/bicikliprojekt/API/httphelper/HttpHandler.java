/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.API.httphelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Denes
 */
public class HttpHandler {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    public static HttpResponse executeGetRequest(String urlAddress) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("Accept", "application/json");

            int statusCode = connection.getResponseCode();

            //Succes-> INputStream, error-> ErrorStream
            InputStream stream = (statusCode >= 200 && statusCode < 300) 
                    ? connection.getInputStream() 
                    : connection.getErrorStream();

            String body = readStream(stream);

            return new HttpResponse(statusCode, body);

        } catch (IOException e) {
            System.err.println("Hálózati hiba a kérés során: " + e.getMessage());
            return new HttpResponse(-1, null);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String readStream(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
