/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public class LopottBicikli extends BaseBike {
    private String reportedDate;
    private String source;

    public LopottBicikli(int id, String brand, String model, String serialNumber, String reportedDate, String source) {
        super(id, brand, model, serialNumber);
        setReportedDate(reportedDate);
        setSource(source);
    }

    public String getReportedDate() { return reportedDate; }
    public String getSource() { return source; }

    public void setReportedDate(String reportedDate) {
        if (reportedDate == null || reportedDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Reported date cannot be empty.");
        }
        this.reportedDate = reportedDate.trim();
    }

    public void setSource(String source) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty.");
        }
        this.source = source.trim();
    }
}
