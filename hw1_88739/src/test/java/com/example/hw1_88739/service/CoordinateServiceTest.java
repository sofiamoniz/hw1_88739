package com.example.hw1_88739.service;

import com.example.hw1_88739.entities.Coordinate;
import com.example.hw1_88739.repository.CoordinateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CoordinateServiceTest {
    private Coordinate coordinate;

    @Mock(lenient=true)
    private CoordinateRepository coordinateRepository;

    @InjectMocks
    private CoordinateService coordinateService;

    @BeforeEach
    public void setUp() throws Exception {
        Coordinate viseuCoor = new Coordinate("Viseu,Portugal",40.6652423, -7.9161281);
        Mockito.when(coordinateRepository.findByPlace("Viseu,Portugal")).thenReturn(viseuCoor);
        Mockito.when(coordinateRepository.findByPlace("none")).thenReturn(null);
    }

    @Test
    public void whenFindByPlace_thenReturnCoor() {
        Coordinate found = coordinateRepository.findByPlace("Viseu,Portugal");
        assertThat(found.getLatitude()).isEqualTo(40.6652423);
        assertThat(found.getLongitude()).isEqualTo(-7.9161281);
    }

    @Test
    public void whenFindByPlace_thenReturnCorrectPlace() {
        Coordinate found = coordinateRepository.findByPlace("Viseu,Portugal");
        assertThat(found.getPlace()).isEqualTo("Viseu,Portugal");
    }

    @Test
    public void whenInvalidPlace_thenReturnNull() {
        Coordinate fromDb = coordinateRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }


    @Test
    public void whenEmptyConstructor_thenReturnNull(){
        Coordinate empty = new Coordinate();
        assertThat(coordinateService.getPlaceCoordinate((empty.getPlace()))).isNull();
    }

}