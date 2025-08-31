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

import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.services.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping
    public List<RegisterModel> getAll() {
        return registerService.getAllRegisters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterModel> getById(@PathVariable String id) {
        return registerService.getRegisterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RegisterModel create(@RequestBody RegisterModel register) {
        return registerService.saveRegister(register);
    }

    @PutMapping("/{name}")
    public ResponseEntity<RegisterModel> update(@PathVariable String name, @RequestBody RegisterModel register) {
        register.setName(name);
        registerService.writeRegister(name, register.getValue());
        return ResponseEntity.ok(register);
    }
}
