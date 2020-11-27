package com.monnos.api.starwars.service;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.dto.converter.PlanetConverter;
import com.monnos.api.starwars.exception.InstanceAlreadyExistsInDatabaseException;
import com.monnos.api.starwars.exception.PlanetAlredyDeletedException;
import com.monnos.api.starwars.exception.PlanetNotFoundException;
import com.monnos.api.starwars.exception.StarWarsApiUnavailableException;
import com.monnos.api.starwars.model.Planet;
import com.monnos.api.starwars.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Scope("singleton")
public class PlanetService {

    private final PlanetConverter planetConverter = new PlanetConverter();
    private final StarWarsApiConsumerService swapiConsumer = new StarWarsApiConsumerService();

    @Autowired
    private PlanetRepository planetRepository;

    public PlanetDto getPlanet(int id) {
        Optional<Planet> p = planetRepository.findByIdAndAndValidIsTrue(id);
        if(p.isPresent())
            return planetConverter.convertToPlanetDto(p.get());
        throw new PlanetNotFoundException();
    }

    public PlanetDto addPlanet(PlanetDto planetDto) {
        if(planetRepository.findById(planetDto.getId()).isPresent())
            throw new InstanceAlreadyExistsInDatabaseException();
        Planet p = planetConverter.convert(planetDto);

        // sets Film Count fetched from Star Wars API
        PlanetDto swapiPlanet = swapiConsumer.fetchPlanetById(p.getId());
        if(swapiPlanet!=null && swapiPlanet.getFilmCount()!=null)
            p.setFilmCount(swapiPlanet.getFilmCount());

        p.setValid(true);
        planetRepository.save(p);
        return planetConverter.convertToPlanetDto(p);
    }

    public void deletePlanet(int id) {
        Optional<Planet> optionalPlanet = planetRepository.findById(id);
        Planet planet = optionalPlanet.orElseThrow(PlanetNotFoundException::new);
        if(!planet.isValid())
            throw new PlanetAlredyDeletedException();
        planet.setValid(false);
        planetRepository.save(planet);
    }

    public List<PlanetDto> findPlanetByName(String name) {
        List<Planet> planets = planetRepository.findByNameContainingAndValidIsTrue(name);
        return planetConverter.convertToPlanetDto(planets);
    }

    public List<PlanetDto> findAllInDatabase() {
        return planetConverter.convertToPlanetDto(planetRepository.findByValidIsTrue());
    }

    public List<PlanetDto> findAllInStarWarsApi() {
        List<PlanetDto> planets = swapiConsumer.fetchAllPlanets();
        if(!planets.isEmpty())  return planets;
        else throw new StarWarsApiUnavailableException();
    }

}
