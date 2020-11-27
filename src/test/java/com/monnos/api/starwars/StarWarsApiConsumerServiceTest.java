package com.monnos.api.starwars;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.exception.PlanetNotFoundException;
import com.monnos.api.starwars.service.StarWarsApiConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StarWarsApiConsumerServiceTest {

    String URL_SWAPI_PLANETS = "https://swapi.dev/api/planets/";
    StarWarsApiConsumerService swapiConsumer = new StarWarsApiConsumerService();

    @Test
    void fetchAllPlanets_shouldReturnListWithMoreThanOnePage() {
        List<PlanetDto> planets = swapiConsumer.fetchAllPlanets();
        assertNotNull(planets);
        assertFalse(planets.isEmpty());
        assertNotEquals(1, planets.size());  // expected result fetching by ID
        assertNotEquals(10, planets.size()); // pagination size
    }

    @Test
    void fetchAllPlanets_shouldReturnListWithSizeCompatibleWithApiDescription() {
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Object> response = restTemplate.getForObject(URL_SWAPI_PLANETS, new HashMap<>().getClass());
        assertNotNull(response);
        Integer count = Integer.valueOf(response.get("count").toString());

        List<PlanetDto> planets = swapiConsumer.fetchAllPlanets();

        assertNotNull(planets);
        assertFalse(planets.isEmpty());
        assertEquals(count, planets.size());
    }

    @Test
    void fetchPlanetById_ShouldReturnFirstPlanetFromMovies() {
        PlanetDto planet = swapiConsumer.fetchPlanetById(1);
        assertNotNull(planet);
        assertEquals( "Tatooine", planet.getName());
        assertEquals( "arid", planet.getClimate());
        assertEquals(5, planet.getFilmCount());
    }

    @Test
    void fetchPlanetById_ShouldThrowPlanetNotFoundException() {
        assertThrows(PlanetNotFoundException.class, () -> {
            swapiConsumer.fetchPlanetById(0);
        } );
    }
}