package com.example.hw1_88739.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="coordinates")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinate {

    @Id
    String place;

    double latitude;
    double longitude;

    public Coordinate(){}

    public Coordinate(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude = longitude;
    }

    public Coordinate(String place ,double latitude, double longitude){
        this.place=place;
        this.latitude=latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getPlace() { return place; }

    public double getLongitude(){
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "place='" + place + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
