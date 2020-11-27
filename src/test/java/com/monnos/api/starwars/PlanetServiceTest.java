package com.monnos.api.starwars;

import com.monnos.Application;
import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.exception.InstanceAlreadyExistsInDatabaseException;
import com.monnos.api.starwars.exception.PlanetNotFoundException;
import com.monnos.api.starwars.repository.PlanetRepository;
import com.monnos.api.starwars.service.PlanetService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetServiceTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    @Autowired
    private PlanetService planetService;


    @Test
    public void addPlanet_shouldAddAndReturnValidPlanet() {
        PlanetDto p1 = new PlanetDto();
        p1.setId(4);
        p1.setName("Hoth");
        p1.setClimate("frozen");
        p1.setTerrain("tundra, ice caves, mountain ranges");
        p1.setFilmCount(1);

        PlanetDto p2 = planetService.addPlanet(p1);
        assertNotNull(p2);

        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getName(), p2.getName());
        assertEquals(p1.getClimate(), p2.getClimate());
        assertEquals(p1.getTerrain(), p2.getTerrain());
        assertEquals(p1.getFilmCount(), p2.getFilmCount());
    }

    @Test
    public void addPlanet_shouldThrowInstanceAlredyExistsInDatabaseException() {
        PlanetDto p1 = new PlanetDto();
        p1.setId(4);
        p1.setName("Hoth");
        p1.setClimate("frozen");
        p1.setTerrain("tundra, ice caves, mountain ranges");
        p1.setFilmCount(1);

        assertThrows(InstanceAlreadyExistsInDatabaseException.class, () -> {
            PlanetDto p2 = planetService.addPlanet(p1);
        });
    }

    @Test
    public void deletePlanet_shouldThrowPlanetNotFoundException() {
        assertThrows(PlanetNotFoundException.class, () -> {
            planetService.deletePlanet(5);
        });
    }

    @Test
    public void deletePlanet_shouldNotThrowAnyException() {
        planetService.deletePlanet(4);
    }

    @Test
    public void getPlanetById_shouldReturnPlanetDtoNormally() {
        PlanetDto p1 = new PlanetDto();
        p1.setId(5);
        p1.setName("Dagobah");
        p1.setClimate("murky");
        p1.setTerrain("swamp, jungles");
        p1.setFilmCount(3);
        planetService.addPlanet(p1);

        PlanetDto p2 = planetService.getPlanet(5);

        assertNotNull(p2);
        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getName(), p2.getName());
        assertEquals(p1.getClimate(), p2.getClimate());
        assertEquals(p1.getTerrain(), p2.getTerrain());
        assertEquals(p1.getFilmCount(), p2.getFilmCount());
    }

    @Test
    public void findPlanetByName_shouldReturnListOfPlanetDtoWithOnePlanet() {
        PlanetDto p1 = new PlanetDto();
        p1.setId(8);
        p1.setName("Naboo");
        p1.setClimate("temperate");
        p1.setTerrain("grassy hills, swamps, forests, mountains");
        p1.setFilmCount(4);
        planetService.addPlanet(p1);

        String name = "boo";
        List<PlanetDto> planets = planetService.findPlanetByName(name);
        assertNotNull(planets);
        assertEquals(1, planets.size());
        assertEquals("Naboo",planets.get(0).getName());
    }

    @Test
    public void findPlanetByName_shouldReturnEmptyList() {
        String coruscantPart = "Corusc";
        List<PlanetDto> planets = planetService.findPlanetByName(coruscantPart);
        assertNotNull(planets);
        assertEquals(0, planets.size());
    }

    @Test
    public void findAllInDatabase_shouldReturnJustTestCases() {
        List<PlanetDto> planets = planetService.findAllInDatabase();
        assertNotNull(planets);
        // Only these test-related planets are stored in Monnos database
        assertTrue(planets.size() < 10);
    }

    @Test
    public void findAllInStarWarsApi_shouldReturnMoreThanMonnosApi() {
        List<PlanetDto> planets = planetService.findAllInStarWarsApi();
        List<PlanetDto> planetsMonnos = planetService.findAllInDatabase();

        assertNotNull(planets);
        assertNotEquals(0, planets.size());
        assertNotEquals(1, planets.size());
        assertNotEquals(2, planets.size());
        assertTrue(planets.size() > planetsMonnos.size());
    }
}