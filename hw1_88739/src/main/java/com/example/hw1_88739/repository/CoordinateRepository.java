package com.example.hw1_88739.repository;

import com.example.hw1_88739.entities.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, String> {
    Coordinate findByPlace(String place);
}
