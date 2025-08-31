package com.flavio.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flavio.spring.services.UCService;

@RestController
@RequestMapping("/uc")
public class UCController {

    @Autowired
    private UCService ucService;

    @GetMapping("/state")
    public int getState() {
        return ucService.getPC();
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeInstruction(@RequestBody String instruction) {
        String[] parts = instruction.split(" ");
        String instructionName = parts[0];
        ucService.execute(instructionName, parts.length > 1 ? parts[1] : null, parts.length > 2 ? parts[2] : null);
        return ResponseEntity.ok("Instrucción ejecutada");
    }
}
