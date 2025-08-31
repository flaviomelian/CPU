package com.flavio.spring.services;

import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.repositories.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UCService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private ALUService alu;

    // Contador de programa
    private int PC = 0;

    // Flags
    private boolean zeroFlag = false;
    private boolean carryFlag = false;

    public void execute(String instruction, String arg1, String arg2) {
        int result;

        switch (instruction.toUpperCase()) {
            case "MOV":
                writeRegister(arg1, readRegister(arg2));
                break;

            case "ADD":
                result = alu.add(readRegister(arg1), readRegister(arg2));
                writeRegister(arg1, result);
                updateFlags(result);
                break;

            case "SUB":
                result = alu.sub(readRegister(arg1), readRegister(arg2));
                writeRegister(arg1, result);
                updateFlags(result);
                break;

            case "INC":
                result = alu.inc(readRegister(arg1));
                writeRegister(arg1, result);
                updateFlags(result);
                break;

            case "DEC":
                result = alu.dec(readRegister(arg1));
                writeRegister(arg1, result);
                updateFlags(result);
                break;

            case "JMP":
                PC = Integer.parseInt(arg1);
                return; // no incrementamos PC automáticamente aquí

            case "JZ":
                if (zeroFlag) {
                    PC = Integer.parseInt(arg1);
                    return;
                }
                break;

            default:
                throw new IllegalArgumentException("Instrucción no soportada: " + instruction);
        }

        PC++; // avanzar PC por defecto
    }

    private void updateFlags(int result) {
        zeroFlag = (result == 0);
        carryFlag = (result > 255 || result < 0); // asumimos 8 bits
    }

    public int readRegister(String name) {
        return registerRepository.findByName(name)
                .map(RegisterModel::getValue)
                .orElse(0);
    }

    public void writeRegister(String name, int value) {
        registerRepository.findByName(name).ifPresent(r -> {
            r.setValue(value & 0xFF); // forzamos 8 bits
            registerRepository.save(r);
        });
    }

    public int getPC() {
        return PC;
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public boolean isCarryFlag() {
        return carryFlag;
    }
}
