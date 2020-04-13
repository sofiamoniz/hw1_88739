package com.example.hw1_88739.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="weathers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @Id
    String place;

    String weather_text;

    Double tempVal;

    String tempUnits;

    Long relative_humidity;

    public Weather(){}

    public Weather(String place, String weather_text, Double tempVal, String tempUnits, Long relative_humidity){
        this.place= place;
        this.weather_text=weather_text;
        this.tempVal=tempVal;
        this.tempUnits=tempUnits;
        this.relative_humidity=relative_humidity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWeather_text() {
        return weather_text;
    }

    public void setWeather_text(String weather_text) {
        this.weather_text = weather_text;
    }

    public Double getTempVal() {
        return tempVal;
    }

    public void setTempVal(Double tempVal) {
        this.tempVal = tempVal;
    }

    public String getTempUnits() {
        return tempUnits;
    }

    public void setTempUnits(String tempUnits) {
        this.tempUnits = tempUnits;
    }

    public Long getRelative_humidity() {
        return relative_humidity;
    }

    public void setRelative_humidity(Long relative_humidity) {
        this.relative_humidity = relative_humidity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "place='" + place + '\'' +
                ", weather_text='" + weather_text + '\'' +
                ", tempVal=" + tempVal +
                ", tempUnits='" + tempUnits + '\'' +
                ", relative_humidity=" + relative_humidity +
                '}';
    }
}
