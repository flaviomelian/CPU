package com.flavio.spring.services;

import com.flavio.spring.models.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ALUService {
    
    @Autowired
    private RegisterService registerService;

    public int add(int reg1, int reg2) {
        return reg1 + reg2;
    }

    public int sub(int reg1, int reg2) {
        return reg1 - reg2;
    }

    public int and(int reg1, int reg2) {
        return reg1 & reg2;
    }

    public int or(int reg1, int reg2) {
        return reg1 | reg2;
    }

    public int not(int reg) {
        return (~reg) & 0xFF;
    }

    public int inc(int reg) {
        return (reg + 1) & 0xFF;
    }

    public int dec(int reg) {
        return (reg - 1) & 0xFF;
    }
    
}