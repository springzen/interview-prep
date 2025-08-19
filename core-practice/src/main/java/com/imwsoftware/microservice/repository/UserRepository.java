package com.imwsoftware.microservice.repository;

import com.imwsoftware.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
