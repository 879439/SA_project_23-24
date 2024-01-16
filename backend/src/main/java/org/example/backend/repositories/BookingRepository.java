package org.example.backend.repositories;
import org.example.backend.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

public interface BookingRepository extends MongoRepository<Booking, String> {
    @Query("{$or:[{one-way: ?0},{round-trip:?0}]}")
    ArrayList<Booking> findByFlightIds(String flightId);
}
