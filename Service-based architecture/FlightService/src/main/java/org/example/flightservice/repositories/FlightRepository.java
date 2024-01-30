package org.example.flightservice.repositories;


import org.example.flightservice.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, String> {

    // Custom queries if needed
    @Query("{$and:[{departure: ?0},{arrival:?1}]}")
    List<Flight> findByCities(String departure, String arrival);
    @Query("{$and:[{departure: ?0},{arrival:?1},{date: ?2}]}")
    List<Flight> findByCitiesAndDate(String departure, String arrival,String Date);
    @Query("{$or:[{$and:[{departure: ?0},{arrival:?1},{date: ?2}]},{$and: [{departure: ?1},{arrival:?0},{date: ?3}]}]}")
    List<Flight> findByCitiesAndDate(String departure, String arrival,String Date,String returnDate);
}
