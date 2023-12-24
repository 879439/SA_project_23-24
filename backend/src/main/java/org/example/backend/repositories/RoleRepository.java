package org.example.backend.repositories;

import java.util.Optional;

import org.example.backend.models.ERole;
import org.example.backend.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
