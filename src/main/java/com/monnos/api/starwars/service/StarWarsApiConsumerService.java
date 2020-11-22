package com.monnos.api.starwars.service;

import com.monnos.api.starwars.dto.PlanetDto;
import com.monnos.api.starwars.dto.PlanetSwapiDto;
import com.monnos.api.starwars.dto.converter.PlanetConverter;
import com.monnos.api.starwars.dto.wrapper.PageableResponse;
import com.monnos.api.starwars.exception.PlanetNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class StarWarsApiConsumerService {

    private static final String URL_SWAPI_PLANETS = "https://swapi.dev/api/planets/";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final PlanetConverter planetConverter = new PlanetConverter();

    public List<PlanetDto> fetchAllPlanets() {
        List<PlanetSwapiDto> planets = new ArrayList<PlanetSwapiDto>();
        String url = URL_SWAPI_PLANETS;

        ParameterizedTypeReference<PageableResponse<PlanetSwapiDto>> responseType
                = new ParameterizedTypeReference<PageableResponse<PlanetSwapiDto>>() { };
        ResponseEntity<PageableResponse<PlanetSwapiDto>> result
                = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        planets.addAll(Objects.requireNonNull(result.getBody()).getContent());

        while(result.getBody().getNext()!=null){
            url = result.getBody().getNext().replaceAll("http", "https");
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            planets.addAll(Objects.requireNonNull(result.getBody()).getContent());
        }
        return planets.stream().map(planetConverter::convert).collect(Collectors.toList());
    }

    public PlanetDto fetchPlanetById(int id) {
        try{
            String url = URL_SWAPI_PLANETS + id + "/";
            ResponseEntity<PlanetSwapiDto> response = restTemplate.getForEntity(url, PlanetSwapiDto.class);
            PlanetSwapiDto planet = response.getBody();
            return planetConverter.convert(planet);
        } catch (HttpClientErrorException ex){
            throw new PlanetNotFoundException();
        }
    }
}
