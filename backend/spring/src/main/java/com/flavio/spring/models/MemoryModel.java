package com.flavio.spring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "memory")
public class MemoryModel {
    @Id
    private int address;   // dirección de cache
    private int value;     // contenido
}

