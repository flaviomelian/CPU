package com.flavio.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flavio.spring.models.PCModel;
import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.repositories.PCRepository;
import com.flavio.spring.repositories.RegisterRepository;

@Service
public class UCService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private ALUService alu;

    @Autowired
    private PCRepository pcRepository;

    private static final String PC_ID = "PC";

    public PCModel getPC() {
        return pcRepository.findById(PC_ID).orElseGet(() -> {
            PCModel pc = new PCModel(PC_ID, 0);
            return pcRepository.save(pc);
        });
    }

    private void incrementPC() {
        PCModel pc = getPC();
        pc.setValue((pc.getValue() + 1) & 0xFF); // límite a 8 bits
        pcRepository.save(pc);
    }

    private void setPC(int value) {
        PCModel pc = getPC();
        pc.setValue(value & 0xFF); // límite a 8 bits
        pcRepository.save(pc);
    }

    public int getPCValue() {
        return getPC().getValue();
    }

    private boolean zeroFlag = false;
    private boolean carryFlag = false;

    public void execute(String instruction, String arg1, String arg2, String arg3) {
        RegisterModel result;

        switch (instruction.toUpperCase()) {
            case "MOV":
                RegisterModel src = readRegister(arg2);
                RegisterModel dest = readRegister(arg1);
                dest.setValue(src.getValue());
                writeRegister(dest);
                break;

            case "ADD":
                result = alu.add(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "SUB":
                result = alu.sub(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "MUL":
                result = alu.mul(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "INC":
                result = alu.inc(readRegister(arg1), readRegister(arg1));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "DEC":
                result = alu.dec(readRegister(arg1), readRegister(arg1));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "JMP":
                setPC(Integer.parseInt(arg3));
                return;

            case "JZ":
                if (zeroFlag) {
                    setPC(Integer.parseInt(arg3));
                    return;
                }
                break;

            default:
                throw new IllegalArgumentException("Instrucción no soportada: " + instruction);
        }

        incrementPC();
    }

    private void updateFlags(int result) {
        zeroFlag = (result == 0);
        carryFlag = (result > 255 || result < 0); // 8 bits
    }

    public RegisterModel readRegister(String name) {
        return registerRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado: " + name));
    }

    public void writeRegister(RegisterModel reg) {
        reg.setValue(reg.getValue() & 0xFF);
        registerRepository.save(reg);
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public boolean isCarryFlag() {
        return carryFlag;
    }
}
