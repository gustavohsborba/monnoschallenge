package com.monnos.api.starwars.service;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.dto.converter.PlanetConverter;
import com.monnos.api.starwars.exception.PlanetAlredyDeletedException;
import com.monnos.api.starwars.exception.PlanetNotFoundException;
import com.monnos.api.starwars.model.Planet;
import com.monnos.api.starwars.repository.PlanetRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Scope("singleton")
public class PlanetService {

    private static PlanetConverter  planetConverter = new PlanetConverter();
    private static PlanetRepository planetRepository;

    public Planet getPlanet(int id) {
        Optional<Planet> p = planetRepository.findById(id);
        return p.orElseThrow(NoSuchElementException::new);
    }

    public void addPlanet(PlanetDto planetDto) {
        if(planetRepository.findById(planetDto.getId()).isPresent())
            throw new InstanceAlreadyExistsException();
        Planet p = planetConverter.convert(planetDto);
        // TODO: Consult SWAPI API to set number of Films
        p.setValid(true);
        planetRepository.save(p);
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
        List<Planet> planets = planetRepository.findByNameContaining();
        // TODO: Consult SWAPI API to get the planet by name
        return planetConverter.convert(planets);
    }

    public List<PlanetDto> findAllInDatabase() {
        return planetConverter.convert(planetRepository.findAll());
    }

    public List<PlanetDto> findAllInSwapi() {
        // TODO: Consult SWAPI API to get list of planets
        return null;
    }

}
