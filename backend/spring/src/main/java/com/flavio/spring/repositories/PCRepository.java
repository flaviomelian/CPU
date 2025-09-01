package com.flavio.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.flavio.spring.models.PCModel;

public interface PCRepository extends JpaRepository<PCModel, String> {}

