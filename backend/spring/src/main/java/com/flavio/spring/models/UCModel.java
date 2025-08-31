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
@Table(name = "uc")
public class UCModel {
    @Id
    private int id;          // identificador único de la UC
    private boolean read;     // si la UC indica leer de memoria
    private boolean write;    // si la UC indica escribir en memoria
    private boolean execute;  // si la UC indica ejecutar instrucción
    private String aluOp;     // operación que debe realizar la ALU, p.ej. "ADD", "SUB"
}
