package org.example.backend.repositories;
import org.example.backend.models.Booking;
import org.example.backend.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    @Query("{$or:[{one-way: ?0},{round-trip:?0}]}")
    ArrayList<Booking> findByFlightIds(String flightId);
    @Query("{userId: ?0}")
    List<Booking> findBookingsByUserId(String userId);
    Optional<Booking> findBookingById(String id);
}
