package com.monnos.api.starwars;

import com.monnos.api.starwars.dto.PlanetSwapiDto;
import com.monnos.api.starwars.dto.wrapper.SwapiPageableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwapiPageableResponseTest extends ApplicationTest {

    private static final String URL_SWAPI_PLANETS = "https://swapi.dev/api/planets/";
    private static final RestTemplate restTemplate = new RestTemplate();


    @Test
    public void testClass(){
        ParameterizedTypeReference<SwapiPageableResponse<PlanetSwapiDto>> responseType;
        ResponseEntity<SwapiPageableResponse<PlanetSwapiDto>> result;

        responseType = new ParameterizedTypeReference<SwapiPageableResponse<PlanetSwapiDto>>() { };
        result = restTemplate.exchange(URL_SWAPI_PLANETS, HttpMethod.GET, null, responseType);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getContent());
        assertTrue(result.getBody().getContent() instanceof List);
        assertNotNull(result.getBody().getNext());
        assertNull(result.getBody().getPrevious());
    }

}