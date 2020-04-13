package com.example.hw1_88739.repository;

import com.example.hw1_88739.entities.AirQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirQualityRepository extends JpaRepository<AirQuality, String> {
    AirQuality findByPlace(String place);
}
