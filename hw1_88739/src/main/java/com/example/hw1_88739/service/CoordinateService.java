package com.example.hw1_88739.service;

import com.example.hw1_88739.entities.Coordinate;
import com.example.hw1_88739.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinateService {

    @Autowired
    CoordinateRepository coordinateRepository;

    public CoordinateService(CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    public Coordinate getPlaceCoordinate(String place){return coordinateRepository.findByPlace(place);}

    public void saveCoordinate (Coordinate coordinate){coordinateRepository.save(coordinate);}

}
