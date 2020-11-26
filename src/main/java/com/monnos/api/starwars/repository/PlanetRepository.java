package com.monnos.api.starwars.repository;

import com.monnos.api.starwars.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends MongoRepository<Planet, Integer> {
    Optional<Planet> findById(int id);
    List<Planet> findByNameContaining(String name);
}
