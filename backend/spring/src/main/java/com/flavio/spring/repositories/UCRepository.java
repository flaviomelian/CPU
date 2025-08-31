package com.flavio.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flavio.spring.models.UCModel;

public interface UCRepository extends JpaRepository<UCModel, Integer>{}