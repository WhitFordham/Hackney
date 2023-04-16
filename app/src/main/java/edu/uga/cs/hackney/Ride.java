package edu.uga.cs.hackney;

public class Ride {
    private String key;
    private String dateAndTime;
    private String startLocation;
    private String endLocation;
    private double price;

    public Ride() {
        this.key = null;
        this.dateAndTime = null;
        this.startLocation = null;
        this.endLocation = null;
        this.price = 0.0;
    }

    public Ride(String dateAndTime, String startLocation, String endLocation, double price) {
        this.key = null;
        this.dateAndTime = dateAndTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
