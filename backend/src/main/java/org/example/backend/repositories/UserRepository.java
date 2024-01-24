package org.example.backend.repositories;

import org.example.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;



public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}