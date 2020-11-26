package com.monnos.api.starwars;

import com.monnos.api.starwars.model.Planet;
import com.monnos.api.starwars.repository.PlanetRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Before
    public void setUp() throws Exception {
        Planet p = new Planet();
        p.setName("Tatooine");
        p.setTerrain("desert");
        p.setClimate("arid");
        p.setFilmCount(5);

        assertNull(p.getId());
        this.planetRepository.save(p);
        assertNotNull(p.getId());
    }


    @Test
    void findById() {
        Optional<Planet> p = this.planetRepository.findById(1);
        assertTrue(p.isPresent());
        assertNotNull(p.get());
        assertEquals("Tatooine", p.get().getName());
    }

    @Test
    void findByNameContaining() {
        List<Planet> list = this.planetRepository.findByNameContaining("oo");
        assertNotNull(list);
        assertNotEquals(0, list.size());
    }

    @Test
    void findAll() {
        List<Planet> list = this.planetRepository.findAll();
        assertNotNull(list);
        assertEquals(1, list.size());
    }
}