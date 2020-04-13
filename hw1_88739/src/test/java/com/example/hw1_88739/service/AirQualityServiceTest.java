package com.example.hw1_88739.service;

import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.repository.AirQualityRepository;
import org.junit.jupiter.api.AfterEach;
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
class AirQualityServiceTest {
    private AirQuality airQuality;

    @Mock(lenient = true)
    private AirQualityRepository airQualityRepository;

    @InjectMocks
    private AirQualityService airQualityService;


    @BeforeEach
    public void setUp() throws Exception {
        AirQuality viseuQuality = new AirQuality("Viseu,Portugal","Good air quality", "o3");
        Mockito.when(airQualityRepository.findByPlace("Viseu,Portugal")).thenReturn(viseuQuality);
        Mockito.when(airQualityRepository.findByPlace("none")).thenReturn(null);
    }

    @Test
    public void whenFindByPlace_thenReturnAirQuality() {
        AirQuality found = airQualityRepository.findByPlace("Viseu,Portugal");
        assertThat(found.getCategory()).isEqualTo("Good air quality");
        assertThat(found.getMainPollutant()).isEqualTo("o3");
        assertThat(found.getPlace()).isEqualTo("Viseu,Portugal");
    }

    @Test
    public void whenInvalidPlace_thenReturnNull() {
        AirQuality fromDb = airQualityRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenEmptyConstructor_thenReturnNull(){
        AirQuality empty = new AirQuality();
        assertThat(airQualityService.getAirQualityByPlace((empty.getPlace()))).isNull();
    }









}