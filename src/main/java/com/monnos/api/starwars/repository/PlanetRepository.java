package com.monnos.api.starwars.repository;

import com.monnos.api.starwars.model.Planet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends CrudRepository<Planet, Integer> {

    Optional<Planet> findById(int id);
    List<Planet> findByNameContaining(String name);
    List<Planet> findAll();
}
