package com.example.hw1_88739.repository;


import com.example.hw1_88739.entities.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {
    Weather findByPlace(String place);
}
