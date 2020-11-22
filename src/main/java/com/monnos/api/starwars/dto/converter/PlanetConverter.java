package com.monnos.api.starwars.dto.converter;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.dto.PlanetSwapiDto;
import com.monnos.api.starwars.exception.NotParseableFieldsException;
import com.monnos.api.starwars.model.Planet;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PlanetConverter implements Converter<PlanetDto, Planet> {

    Pattern commaSeparatedList = Pattern.compile("/[^,(?! )]+/g");

    @Override
    public Planet convert(PlanetDto planetDto) {
        if(!commaSeparatedList.matcher(planetDto.getClimate()).matches())
            throw new NotParseableFieldsException();
        if(!commaSeparatedList.matcher(planetDto.getClimate()).matches())
            throw new NotParseableFieldsException();

        Planet p = new Planet();
        p.setId(planetDto.getId());
        p.setName(planetDto.getName());
        p.setClimate(planetDto.getClimate());
        p.setTerrain(planetDto.getTerrain());
        p.setFilmCount(planetDto.getFilmCount());
        return p;
    }

    public PlanetDto convert(Planet planet) {
        if(planet == null)
            return new PlanetDto();
        PlanetDto p = new PlanetDto();
        p.setId(planet.getId());
        p.setName(planet.getName());
        p.setClimate(planet.getClimate());
        p.setTerrain(planet.getTerrain());
        p.setFilmCount(planet.getFilmCount());
        return p;
    }

    public PlanetDto convert(PlanetSwapiDto planet) {
        if(planet==null)
            return new PlanetDto();
        PlanetDto p = new PlanetDto();
        p.setName(planet.getName());
        p.setClimate(planet.getClimate());
        p.setTerrain(planet.getTerrain());
        p.setFilmCount(planet.getFilms().size());
        return p;
    }

    public List<PlanetDto> convert(List<Planet> planetList) {
        return planetList.stream().map(this::convert).collect(Collectors.toList());
    }
}
