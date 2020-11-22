package com.monnos.api.starwars.dto;

import lombok.Data;

@Data
public class PlanetDto {
    private Integer id;
    private String name;
    private String climate;
    private String terrain;
    private Integer filmCount;
}
