package com.example.hw1_88739.repository;

import com.example.hw1_88739.entities.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CoordinateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Test
    public void whenFindByPlace_thenReturnCoordinates(){
        Coordinate viseuCoor = new Coordinate("Viseu,Portugal",40.6652423, -7.9161281);
        entityManager.persistAndFlush(viseuCoor);
        Coordinate viseuCoorFound = coordinateRepository.findByPlace(viseuCoor.getPlace());
        assertThat(viseuCoorFound).isEqualTo(viseuCoor);
    }

    @Test
    public void whenInvalidName_ThenReturnNull(){
        Coordinate fromDb = coordinateRepository.findByPlace("none");
        assertThat(fromDb).isNull();
    }

}
