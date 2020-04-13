package com.example.hw1_88739.repository;

import com.example.hw1_88739.entities.Weather;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WeatherRepository weatherRepository;


    @Test
    public void whenFindByPlace_thenReturnCorrectWeather(){
        Weather aveiroWeather = new Weather("Aveiro,Portugal","Overcast",17.86,"C", (long) 55);
        entityManager.persistAndFlush(aveiroWeather);
        Weather aveiroWeatherFound = weatherRepository.findByPlace(aveiroWeather.getPlace());
        assertThat(aveiroWeatherFound).isEqualTo(aveiroWeather);
    }

    @Test
    public void whenInvalidName_ThenReturnNull(){
        Weather fromDb = weatherRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }


}