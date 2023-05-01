package edu.uga.cs.hackney;

public class Ride {
    private String key;
    private String date;
    private String time;
    private String startLocation;
    private String endLocation;
    private double price;
    private int peopleConfirmed;
    private String driverID;
    private String riderID;

    public Ride() {
        this.key = null;
        this.date = null;
        this.time = null;
        this.startLocation = null;
        this.endLocation = null;
        this.price = 0.0;
        this.peopleConfirmed = 0;
        this.driverID = null;
        this.riderID = null;
    }

    public Ride(String date, String time, String startLocation, String endLocation, double price) {
        this.key = null;
        this.date = date;
        this.time = time;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.price = price;
        this.peopleConfirmed = 0;
        this.driverID = null;
        this.riderID = null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateAndTime) {
        this.date = dateAndTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getPeopleConfirmed() {return peopleConfirmed;};

    public void addPeopleConfirmed() {
        peopleConfirmed++;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }
}
