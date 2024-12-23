package com.example.unischedule.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    //Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);
}
