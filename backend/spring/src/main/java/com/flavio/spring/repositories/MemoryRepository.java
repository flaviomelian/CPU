package com.flavio.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flavio.spring.models.MemoryModel;

public interface MemoryRepository extends JpaRepository<MemoryModel, Integer>{}