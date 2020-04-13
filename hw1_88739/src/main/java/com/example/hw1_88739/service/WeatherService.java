package com.example.hw1_88739.service;

import com.example.hw1_88739.entities.Weather;
import com.example.hw1_88739.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository){
        this.weatherRepository=weatherRepository;
    }

    public Weather getWeatherByPlace(String place){
        return weatherRepository.findByPlace(place);
    }

    public void saveWeather(Weather weather){
        weatherRepository.save(weather);
    }
}
