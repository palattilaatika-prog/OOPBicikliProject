/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.model;

/**
 *
 * @author Denes
 */
public class StolenBike extends BaseBike {
    private String reportedDate;
    private String source;
    private int reporterUserId;

    public StolenBike(
            int id,
            String brand,
            String model,
            String serialNumber,
            String reportedDate,
            String source
    ) {
        this(id, 0, brand, model, serialNumber, reportedDate, source);
    }
    public StolenBike(
            int id,
            int reporterUserId,
            String brand,
            String model,
            String serialNumber,
            String reportedDate,
            String source
    ) {
        super(id, brand, model, serialNumber);
        setReportedDate(reportedDate);
        setSource(source);
        this.reporterUserId = reporterUserId;
    }

    public String getReportedDate() {
        return reportedDate;
    }

    public String getSource() {
        return source;
    }

    public int getReporterUserId() {
        return reporterUserId;
    }

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
