package com.flavio.spring.services;

import com.flavio.spring.models.MemoryModel;
import com.flavio.spring.repositories.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoryService {

    @Autowired
    private MemoryRepository repository;

    public List<MemoryModel> getAllMemories() {
        return repository.findAll();
    }

    public Optional<MemoryModel> getMemoryById(Integer id) {
        return repository.findById(id);
    }

    public MemoryModel saveMemory(MemoryModel Memory) {
        return repository.save(Memory);
    }

    public MemoryModel writeMemory(int address, int value) {
        Optional<MemoryModel> memOpt = repository.findById(address);
        MemoryModel mem = memOpt.orElse(new MemoryModel(address, value));
        mem.setValue(value);
        return repository.save(mem);
    }

}