package com.flavio.spring.services;

import com.flavio.spring.models.RegisterModel;
import org.springframework.stereotype.Service;

@Service
public class ALUService {

    public RegisterModel add(RegisterModel dest, RegisterModel reg1, RegisterModel reg2) {
        int result = reg1.getValue() + reg2.getValue();
        dest.setValue(result & 0xFF); // Aseguramos que el valor esté en 8 bits
        return dest;
    }

    public RegisterModel sub(RegisterModel dest, RegisterModel reg1, RegisterModel reg2) {
        int result = reg1.getValue() - reg2.getValue();
        dest.setValue(result & 0xFF); // limitar a 8 bits
        return dest;
    }

    public RegisterModel mul(RegisterModel dest, RegisterModel reg1, RegisterModel reg2) {
        int result = reg1.getValue() * reg2.getValue();
        dest.setValue(result & 0xFF); // limitar a 8 bits
        return dest;
    }

    public RegisterModel and(RegisterModel dest, RegisterModel reg1, RegisterModel reg2) {
        int result = reg1.getValue() & reg2.getValue();
        dest.setValue(result & 0xFF);
        return dest;
    }

    public RegisterModel or(RegisterModel dest, RegisterModel reg1, RegisterModel reg2) {
        int result = reg1.getValue() | reg2.getValue();
        dest.setValue(result & 0xFF);
        return dest;
    }

    public RegisterModel not(RegisterModel reg, RegisterModel dest) {
        int result = (~reg.getValue()) & 0xFF;
        dest.setValue(result);
        return dest;
    }

    public RegisterModel inc(RegisterModel reg, RegisterModel dest) {
        int result = (reg.getValue() + 1) & 0xFF;
        dest.setValue(result);
        return dest;
    }

    public RegisterModel dec(RegisterModel reg, RegisterModel dest) {
        int result = (reg.getValue() - 1) & 0xFF;
        dest.setValue(result);
        return dest;
    }

}