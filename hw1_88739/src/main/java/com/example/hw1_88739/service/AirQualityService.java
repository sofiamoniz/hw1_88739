package com.example.hw1_88739.service;

import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.repository.AirQualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirQualityService {

    @Autowired
    AirQualityRepository airQualityRepository;

    public AirQualityService(AirQualityRepository airQualityRepository){
        this.airQualityRepository = airQualityRepository;
    }

    public AirQuality getAirQualityByPlace(String place){
        return airQualityRepository.findByPlace(place);
    }

    public void saveAirQuality (AirQuality airQuality){airQualityRepository.save(airQuality);}



}
