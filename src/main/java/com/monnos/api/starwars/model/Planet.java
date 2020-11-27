package com.monnos.api.starwars.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "planets")
public class Planet {

    @Id
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String climate;

    @NotNull
    private String terrain;

    @NotNull
    private Integer filmCount;

    @NotNull
    private boolean valid;
}
