    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.API;
import com.google.gson.annotations.SerializedName;
/**
 *
 * @author Denes
 */
public class BikeApiItem {
    private int id;
    
    @SerializedName("manufacturer_name")
    private String manufacturerName;
    
    @SerializedName("frame_model")
    private String frameModel;
    
    private String serial;
    
    @SerializedName("date_stolen")
    private Long dateStolen;

    public int getId() { return id; }
    public String getManufacturerName() { return manufacturerName; }
    public String getFrameModel() { return frameModel; }
    public String getSerial() { return serial; }
    public Long getDateStolen() { return dateStolen; }
}
