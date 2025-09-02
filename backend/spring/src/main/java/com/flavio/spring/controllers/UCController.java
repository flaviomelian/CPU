package com.flavio.spring.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavio.spring.models.PCModel;
import com.flavio.spring.services.UCService;

@RestController
@RequestMapping("/uc")
public class UCController {

    @Autowired
    private UCService ucService;

    @GetMapping("/state")
    public PCModel getState() {
        return ucService.getPC();
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeInstruction(@RequestBody Map<String, String> body) {
        String instruction = body.get("instruction");
        String[] parts = instruction.split(" ");
        String instructionName = parts[0];
        ucService.execute(instructionName, parts.length > 1 ? parts[1] : null, parts.length > 2 ? parts[2] : null,
                parts.length > 3 ? parts[3] : null);
        return ResponseEntity.ok("Instrucción ejecutada");
    }

    @PostMapping("/execute-routine")
    public ResponseEntity<String> executeRoutine(@RequestBody Map<String, String> body) {
        String filePath = body.get("filePath");

        if (filePath == null || filePath.isEmpty()) 
            return ResponseEntity.badRequest().body("Se requiere filePath");
        

        File file = new File(filePath);
        if (!file.exists())
            return ResponseEntity.badRequest().body("Archivo no encontrado: " + filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while (br.ready()) {
                line = br.readLine().trim();
                if (line.isEmpty() || line.startsWith("#"))
                    continue; // ignorar líneas vacías o comentarios
                String[] parts = line.split(" "); // separa por espacios
                String instruction = parts[0];
                String arg1 = parts.length > 1 ? parts[1] : null;
                String arg2 = parts.length > 2 ? parts[2] : null;
                String arg3 = parts.length > 3 ? parts[3] : null;

                ucService.execute(instruction, arg1, arg2, arg3);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error ejecutando rutina: " + e.getMessage());
        }

        return ResponseEntity.ok("Rutina ejecutada correctamente");
    }

}
