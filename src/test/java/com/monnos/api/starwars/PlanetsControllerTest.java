package com.monnos.api.starwars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monnos.Application;
import com.monnos.api.starwars.controller.PlanetsController;
import com.monnos.api.starwars.dto.PlanetDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetsControllerTest {

    private static final String URL_PLANETS = "/planets";
    private static final String URL_GET_ALL_PLANNETS_MONNOS = "/planets/all?database=monnos";
    private static final String URL_GET_ALL_PLANNETS_SWAPI = "/planets/all?database=swapi";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlanetsController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void addPlanet_shouldAddAndReturnCreatedStatusWithPlanetJSON() throws Exception {
        String tatooine = "{\"id\":1,\"name\":\"Tatooine\",\"climate\":\"arid\",\"terrain\":\"desert\"}";
        String aldearaan = "{\"id\":2,\"name\":\"Alderaan\",\"climate\":\"temperate\",\"terrain\":\"grasslands, mountains\"}";

        ResultActions resultActions = mockMvc.perform(
                post(URL_PLANETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tatooine))
                .andExpect(status().isCreated());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        PlanetDto planetTatooine = objectMapper.readValue(contentAsString, PlanetDto.class);

        resultActions = mockMvc.perform(
                post(URL_PLANETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aldearaan))
                .andExpect(status().isCreated());
        contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        PlanetDto planetAldearaan = objectMapper.readValue(contentAsString, PlanetDto.class);

        assertEquals("Tatooine", planetTatooine.getName());
        assertEquals("arid", planetTatooine.getClimate());
        assertEquals("desert", planetTatooine.getTerrain());

        assertEquals("Alderaan", planetAldearaan.getName());
        assertEquals("temperate", planetAldearaan.getClimate());
        assertEquals("grasslands, mountains", planetAldearaan.getTerrain());
    }

    @Test
    public void addPlanet_shouldReturnUnprocessableEntityAndEmptyBody() throws Exception {
        String wrongTatooine = "{\"id\":3,\"name\":\"Tatooine\",\"climate\":\"arid\",\"terrain\":\"desert !] not v*alid\"}";
        ResultActions resultActions = mockMvc.perform(
                post(URL_PLANETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongTatooine))
                .andExpect(status().isUnprocessableEntity());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("", contentAsString);
    }


    @Test
    public void addPlanet_shouldReturnBadRequestAndEmptyBody() throws Exception {
        String hello = "Hello World!";
        ResultActions resultActions = mockMvc.perform(
                post(URL_PLANETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hello))
                .andExpect(status().isBadRequest());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("", contentAsString);
    }

    @Test
    public void listPlanetsFromDatabaseSwapi_shouldReturnAllPlanetsFromSWAPI() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(URL_GET_ALL_PLANNETS_SWAPI))
                .andExpect(status().isOk())
                .andDo(print());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        var planets = objectMapper.readValue(contentAsString, List.class);
        assertNotNull(planets);
        assertNotEquals(0, planets.size());
        assertNotEquals(1, planets.size());
    }

    @Test
    public void listPlanetsFromDatabaseMonnos_shouldReturnAllPlanetsFromMonnosDatabase() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(URL_GET_ALL_PLANNETS_MONNOS))
                .andExpect(status().isOk())
                .andDo(print());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        var planets = objectMapper.readValue(contentAsString, List.class);
        assertNotNull(planets);
        assertNotEquals(0, planets.size());
        assertNotEquals(1, planets.size());
        assertEquals(2, planets.size());
    }

    @Test
    public void searchPlanetsByName_shouldFindTatooine() throws Exception {
        String urlSearchTatoo = URL_PLANETS + "?name=Tatoo";
        ResultActions resultActions = mockMvc.perform(get(urlSearchTatoo))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        PlanetDto planets[] = objectMapper.readValue(contentAsString, PlanetDto[].class);
        assertNotNull(planets);
        assertNotEquals(0, planets.length);
        assertEquals(1, planets.length);
        assertEquals("Tatooine", planets[0].getName());
    }

    @Test
    public void searchPlanetsByName_shouldFindNothing() throws Exception {
        String urlSearchTatoo = URL_PLANETS + "?name=Coruscant";
        ResultActions resultActions = mockMvc.perform(get(urlSearchTatoo))
                .andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        PlanetDto planets[] = objectMapper.readValue(contentAsString, PlanetDto[].class);
        assertNotNull(planets);
        assertEquals(0, planets.length);
    }

    @Test
    public void deletePlanet_shouldDeleteAldearaanAndReturnNoContentStatus() throws Exception {
        String urlDeleteAldearaan = URL_PLANETS + "/2";
        ResultActions resultActions = mockMvc.perform(delete(urlDeleteAldearaan))
                .andExpect(status().isNoContent());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("", contentAsString);
    }

    @Test
    public void deletePlanet_shouldReturnGoneStatus() throws Exception {
        String urlDeleteInexistentPlanetInDatabase = URL_PLANETS + "/2";
        ResultActions resultActions = mockMvc.perform(delete(urlDeleteInexistentPlanetInDatabase))
                .andExpect(status().isGone());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("", contentAsString);
    }

    @Test
    public void deletePlanet_shouldReturnNotFoundStatus() throws Exception {
        String urlDeleteInexistentPlanetInDatabase = URL_PLANETS + "/30";
        ResultActions resultActions = mockMvc.perform(delete(urlDeleteInexistentPlanetInDatabase))
                .andExpect(status().isNotFound());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("", contentAsString);
    }
}