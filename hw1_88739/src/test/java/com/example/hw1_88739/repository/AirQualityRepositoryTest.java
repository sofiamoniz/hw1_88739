package com.example.hw1_88739.repository;

import com.example.hw1_88739.entities.AirQuality;
import com.example.hw1_88739.entities.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AirQualityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AirQualityRepository airQualityRepository;

    @Test
    public void whenFindByPlace_thenReturnAirQuality(){
        AirQuality viseuQuality = new AirQuality("Viseu,Portugal","Good air quality", "o3");
        entityManager.persistAndFlush(viseuQuality);
        AirQuality viseuQualityFound = airQualityRepository.findByPlace(viseuQuality.getPlace());
        assertThat(viseuQualityFound).isEqualTo(viseuQuality);
    }

    @Test
    public void whenInvalidName_ThenReturnNull(){
        AirQuality fromDb = airQualityRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }

}
