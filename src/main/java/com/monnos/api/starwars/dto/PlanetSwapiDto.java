package com.monnos.api.starwars.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetSwapiDto {
    private String name;
    private String climate;
    private String terrain;
    private List<String> films;
}
