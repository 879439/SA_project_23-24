package org.example.backend.repositories;
import org.example.backend.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    ArrayList<Booking> findByFlightId(String flightId);
}
