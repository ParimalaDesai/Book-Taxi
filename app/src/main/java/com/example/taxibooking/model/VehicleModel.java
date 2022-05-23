package com.example.taxibooking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class VehicleModel implements Serializable {

    private int vehicle_Id;
    private double vehicle_Lat;
    private double vehicle_Longi;
    private String vehicle_Name;
    private double vehicle_Time;

    public int getVehicle_Id() {
        return vehicle_Id;
    }

    public void setVehicle_Id(int vehicle_Id) {
        this.vehicle_Id = vehicle_Id;
    }

    public double getVehicle_Lat() {
        return vehicle_Lat;
    }

    public void setVehicle_Lat(double vehicle_Lat) {
        this.vehicle_Lat = vehicle_Lat;
    }

    public double getVehicle_Longi() {
        return vehicle_Longi;
    }

    public void setVehicle_Longi(double vehicle_Longi) {
        this.vehicle_Longi = vehicle_Longi;
    }

    public String getVehicle_Name() {
        return vehicle_Name;
    }

    public void setVehicle_Name(String vehicle_Name) {
        this.vehicle_Name = vehicle_Name;
    }

    public double getVehicle_Time() {
        return vehicle_Time;
    }

    public void setVehicle_Time(double vehicle_Time) {
        this.vehicle_Time = vehicle_Time;
    }
}
