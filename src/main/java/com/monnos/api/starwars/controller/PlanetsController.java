package com.monnos.api.starwars.controller;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.exception.NotParseableFieldsException;
import com.monnos.api.starwars.service.PlanetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetsController {

    private final PlanetService planetService;
    public PlanetsController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody PlanetDto addPlanet(@RequestBody PlanetDto planetDto) {
        return  planetService.addPlanet(planetDto);
    }

    @GetMapping(value = "/all", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetDto> listPlanetsFromDatabase(
            @RequestParam(value = "database", required = false, defaultValue = "monnos") String database){
       if(database.equals("swapi"))
           return planetService.findAllInStarWarsApi();
       else if(database.equals("monnos"))
           return planetService.findAllInDatabase();
       else throw new NotParseableFieldsException();
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PlanetDto> searchPlanetsByName(@RequestParam(value = "name", required = true) String name){
        return planetService.findPlanetByName(name);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlanet(@PathVariable int id){
        planetService.deletePlanet(id);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PlanetDto getPlanet(@PathVariable int id){
        return planetService.getPlanet(id);
    }

}
