package com.monnos.api.starwars.controller;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.model.Planet;
import com.monnos.api.starwars.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
public class PlanetsController {

    private final PlanetService planetService;

    public PlanetsController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Planet getPlanet(@PathVariable int id){
        return planetService.getPlanet(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlanet(@RequestBody PlanetDto planetDto){
        planetService.addPlanet(planetDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlanet(@PathVariable int id){
        planetService.deletePlanet(id);
    }

}
