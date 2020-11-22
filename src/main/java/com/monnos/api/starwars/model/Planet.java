package com.monnos.api.starwars.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Planet {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String climate;

    @NotNull
    @Column(nullable = false)
    private String terrain;

    @NotNull
    @Column(nullable = false)
    private Integer filmCount;

    @NotNull
    @Column(nullable = false)
    private boolean valid;
}
