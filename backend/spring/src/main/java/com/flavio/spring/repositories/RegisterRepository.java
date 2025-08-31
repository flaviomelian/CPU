package com.flavio.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flavio.spring.models.RegisterModel;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<RegisterModel, String>{
    @Query("SELECT r FROM RegisterModel r WHERE r.name = ?1")
    Optional<RegisterModel> findByName(String name);
}