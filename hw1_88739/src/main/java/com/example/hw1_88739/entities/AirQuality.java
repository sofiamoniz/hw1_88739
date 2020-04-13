package com.example.hw1_88739.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="airQualities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality {

    @Id
    String place;

    String category;

    String mainPollutant;

    public AirQuality(){};

    public AirQuality(String place){
        this.place = place;
    }

    public AirQuality(String place, String category, String mainPollutant){
        this.place = place;
        this.category=category;
        this.mainPollutant=mainPollutant;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainPollutant() {
        return mainPollutant;
    }

    public void setMainPollutant(String mainPollutant) {
        this.mainPollutant = mainPollutant;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "place='" + place + '\'' +
                ", category='" + category + '\'' +
                ", mainPollutant='" + mainPollutant + '\'' +
                '}';
    }
}
