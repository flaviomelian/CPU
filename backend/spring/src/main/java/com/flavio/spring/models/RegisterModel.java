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
@Table(name = "register")
public class RegisterModel {
    @Id
    private String name;  // nombre del registro, p.ej. "R1"
    private int value;    // contenido del registro
}
