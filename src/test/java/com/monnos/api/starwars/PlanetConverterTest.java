package com.monnos.api.starwars;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.dto.PlanetSwapiDto;
import com.monnos.api.starwars.dto.converter.PlanetConverter;
import com.monnos.api.starwars.exception.NotParseableFieldsException;
import com.monnos.api.starwars.model.Planet;
import com.monnos.api.starwars.service.StarWarsApiConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetConverterTest {

    PlanetConverter planetConverter = new PlanetConverter();
    StarWarsApiConsumerService swapiConsumer = new StarWarsApiConsumerService();

    @Test
    void convert_shouldConvertPlanetDtoToPlanet() {
        PlanetDto planetDto = new PlanetDto();
        planetDto.setId(1);
        planetDto.setName("Tatooine");
        planetDto.setClimate("arid");
        planetDto.setTerrain("desert");
        planetDto.setFilmCount(5);

        Planet planet = planetConverter.convert(planetDto);
        assertNotNull(planet);

        assertEquals(planetDto.getId(), planet.getId());
        assertEquals(planetDto.getName(), planet.getName());
        assertEquals(planetDto.getClimate(), planet.getClimate());
        assertEquals(planetDto.getTerrain(), planet.getTerrain());
        assertEquals(planetDto.getFilmCount(), planet.getFilmCount());
    }

    @Test
    void convert_shouldThrowNotParseableFieldsException() {
        PlanetDto planetDto = new PlanetDto();
        planetDto.setId(1);
        planetDto.setName("Tatooine");
        planetDto.setClimate("arid");
        planetDto.setTerrain("]![ salamander 33432, desert");
        planetDto.setFilmCount(5);

        assertThrows(NotParseableFieldsException.class, () -> {
            Planet planet = planetConverter.convert(planetDto);
        });
    }

    @Test
    void convertToPlanetDto_shouldConvertPlanetToPlanetDto() {
        Planet planet = new Planet();
        planet.setId(1);
        planet.setName("Tatooine");
        planet.setClimate("arid");
        planet.setTerrain("desert");
        planet.setFilmCount(5);

        PlanetDto planetDto = planetConverter.convertToPlanetDto(planet);
        assertNotNull(planetDto);

        assertEquals(planet.getId(), planetDto.getId());
        assertEquals(planet.getName(), planetDto.getName());
        assertEquals(planet.getClimate(), planetDto.getClimate());
        assertEquals(planet.getTerrain(), planetDto.getTerrain());
        assertEquals(planet.getFilmCount(), planetDto.getFilmCount());
    }

    @Test
    void convertPlanetSwapiDtoToPlanetDto() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PlanetSwapiDto> response = restTemplate.getForEntity("https://swapi.dev/api/planets/1/", PlanetSwapiDto.class);
        PlanetSwapiDto planet = response.getBody();

        assertNotNull(planet);
        PlanetDto planetDto = planetConverter.convertToPlanetDto(planet);

        assertEquals(planet.getName(), planetDto.getName());
        assertEquals(planet.getClimate(), planetDto.getClimate());
        assertEquals(planet.getTerrain(), planetDto.getTerrain());
        assertEquals(planet.getFilms().size(), planetDto.getFilmCount());
    }

    @Test
    void convertListOfPlanetDto() {
        Planet p1 = new Planet();
        p1.setId(1);
        p1.setName("Tatooine");
        p1.setClimate("arid");
        p1.setTerrain("desert");
        p1.setFilmCount(5);

        Planet p2 = new Planet();
        p2.setId(2);
        p2.setName("Alderaan");
        p2.setClimate("temperate");
        p2.setTerrain("grasslands, mountains");
        p2.setFilmCount(2);

        List<Planet> planets = new ArrayList<Planet>();
        planets.add(p1);
        planets.add(p2);

        List<PlanetDto> planetDtos = planetConverter.convertToPlanetDto(planets);
        for(int i=0; i<planets.size(); i++){
            assertEquals(planets.get(i).getName(), planetDtos.get(i).getName());
            assertEquals(planets.get(i).getClimate(), planetDtos.get(i).getClimate());
            assertEquals(planets.get(i).getTerrain(), planetDtos.get(i).getTerrain());
            assertEquals(planets.get(i).getFilmCount(), planetDtos.get(i).getFilmCount());
        }

    }
}