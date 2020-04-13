package com.example.hw1_88739.service;


import com.example.hw1_88739.entities.Weather;
import com.example.hw1_88739.repository.WeatherRepository;
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
class WeatherServiceTest {

    @Mock(lenient = true)
    WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() throws Exception {
        Weather aveiroWeather = new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55);
        Mockito.when(weatherRepository.findByPlace("Aveiro,Portugal")).thenReturn(aveiroWeather);
        Mockito.when(weatherRepository.findByPlace("none")).thenReturn(null);
    }

    @Test
    public void whenFindByPlace_thenReturnWeather() {
        Weather found = weatherRepository.findByPlace("Aveiro,Portugal");
        assertThat(found.getPlace()).isEqualTo("Aveiro,Portugal");
        assertThat(found.getWeather_text()).isEqualTo("Overcast");
        assertThat(found.getTempVal()).isEqualTo(17.86);
        assertThat(found.getTempUnits()).isEqualTo("C");
        assertThat(found.getRelative_humidity()).isEqualTo(55);
    }
    @Test
    public void whenInvalidPlace_thenReturnNull() {
        Weather fromDb = weatherRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }
    @Test
    public void whenEmptyConstructor_thenReturnNull(){
        Weather empty = new Weather();
        assertThat(weatherService.getWeatherByPlace(empty.getPlace())).isNull();
    }
}