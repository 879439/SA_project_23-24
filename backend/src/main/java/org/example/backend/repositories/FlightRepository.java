package org.example.backend.repositories;

import org.example.backend.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface FlightRepository extends MongoRepository<Flight, String> {

    // Custom queries if needed
    @Query("{$and:[{departure: ?0},{arrival:?1}]}")
    List<Flight> findByCities(String departure, String arrival);
    @Query("{$and:[{departure: ?0},{arrival:?1},{date: ?2}]}")
    List<Flight> findByCitiesAndDate(String departure, String arrival,String Date);
}
