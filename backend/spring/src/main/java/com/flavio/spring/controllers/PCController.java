package com.flavio.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.flavio.spring.services.PCService;

@RestController
@RequestMapping("/pc")
public class PCController {

    private final PCService pcService;

    public PCController(PCService pcService) {
        this.pcService = pcService;
    }

    @GetMapping
    public ResponseEntity<String> getPC() {
        return ResponseEntity.ok(String.format("0x%02X", pcService.getPC().getValue()));
    }
}
