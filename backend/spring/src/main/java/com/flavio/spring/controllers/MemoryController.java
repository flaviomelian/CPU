package com.flavio.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavio.spring.models.MemoryModel;
import com.flavio.spring.services.MemoryService;

@RestController
@RequestMapping("/memory")
public class MemoryController {

    @Autowired
    private MemoryService memoryService;

    @GetMapping
    public List<MemoryModel> getAll() {
        return memoryService.getAllMemories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoryModel> getById(@PathVariable Integer id) {
        return memoryService.getMemoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MemoryModel create(@RequestBody MemoryModel memory) {
        return memoryService.saveMemory(memory);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetMemory(@RequestBody MemoryModel memory) {
        memoryService.reset(); // pones todos los valores a 0
        return ResponseEntity.ok("Memoria reseteada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoryModel> write(@PathVariable Long id, @RequestBody MemoryModel memory) {
        memory.setAddress(id.intValue());
        memoryService.writeMemory(id.intValue(), memory.getValue());
        return ResponseEntity.ok(memory);
    }
}