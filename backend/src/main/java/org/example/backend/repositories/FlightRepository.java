package org.example.backend.repositories;

import org.example.backend.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, String> {

    // Custom queries if needed
}
