package com.flavio.spring.services;

import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.repositories.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository repository;

    public List<RegisterModel> getAllRegisters() {
        return repository.findAll();
    }

    public Optional<RegisterModel> getRegisterById(String id) {
        return repository.findById(id);
    }

    public RegisterModel saveRegister(RegisterModel Register) {
        return repository.save(Register);
    }

    public RegisterModel writeRegister(String name, int value) {
        Optional<RegisterModel> memOpt = repository.findByName(name);
        RegisterModel mem = memOpt.orElse(new RegisterModel(name, value));
        mem.setValue(value);
        return repository.save(mem);
    }

}