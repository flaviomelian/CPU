package com.flavio.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flavio.spring.models.PCModel;
import com.flavio.spring.repositories.PCRepository;

@Service
public class PCService {

    @Autowired
    private PCRepository pcRepository;

    // Devuelve el PC
    public PCModel getPC() {
        return pcRepository.findById("PC")
                .orElseThrow(() -> new RuntimeException("PC no encontrado"));
    }

    // Incrementa el PC y lo guarda
    public void incrementPC() {
        PCModel pc = getPC();
        pc.setValue((pc.getValue() + 1) & 0xFF); // 8 bits
        pcRepository.save(pc);
    }

    // Setear PC a un valor específico (p. ej., JMP)
    public void setPC(int value) {
        PCModel pc = getPC();
        pc.setValue(value & 0xFF);
        pcRepository.save(pc);
    }
}
