package org.example.backend.repositories;

import org.example.backend.models.ERole;
import org.example.backend.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;



public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
