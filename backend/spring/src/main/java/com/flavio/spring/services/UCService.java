package com.flavio.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flavio.spring.models.MemoryModel;
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

    @Autowired
    private MemoryService memoryService;

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
        RegisterModel dest, src;

        switch (instruction.toUpperCase()) {
            case "MOV":
                src = readRegister(arg1);
                dest = readRegister(arg2);
                dest.setValue(src.getValue());
                writeRegister(dest);
                src.setValue(0);
                writeRegister(src);
                break;

            case "COP":
                src = readRegister(arg1);
                dest = readRegister(arg2);
                dest.setValue(src.getValue());
                writeRegister(dest);
                break;

            case "ADD":
                if (arg1 == null || arg2 == null || arg3 == null)
                    throw new IllegalArgumentException("ADD requiere 3 argumentos: src1, src2, dest");
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
                if (arg1.startsWith("0x"))
                    setPC(Integer.parseInt(arg1.substring(2), 16));
                else
                setPC(Integer.parseInt(arg1));
                return;

            case "JZ":
                RegisterModel isZero = readRegister(arg1);
                if (isZero.getValue() == 0) {
                    if (arg2.startsWith("0x")) {
                        int targetAddress = Integer.parseInt(arg2.substring(2), 16);
                        setPC(targetAddress);
                    } else
                        throw new IllegalArgumentException("JZ requiere una dirección de memoria con formato 0xNN");

                    return;
                }
                break;

            case "LD":
                // LD destRegister, address
                dest = readRegister(arg1);
                int address = parseAddress(arg2);
                MemoryModel mem = memoryService.getMemoryById(address)
                        .orElseThrow(() -> new IllegalArgumentException("Dirección inválida: " + arg2));
                dest.setValue(mem.getValue());
                writeRegister(dest);
                break;

            case "ST":
                // ST srcRegister, address
                src = readRegister(arg1);
                int addr = parseAddress(arg2);
                memoryService.writeMemory(addr, src.getValue());
                break;

            case "AND":
                if (arg1 == null || arg2 == null || arg3 == null)
                    throw new IllegalArgumentException("AND requiere 3 argumentos: src1, src2, dest");
                result = alu.and(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                break;

            case "OR":
                if (arg1 == null || arg2 == null || arg3 == null)
                    throw new IllegalArgumentException("AND requiere 3 argumentos: src1, src2, dest");
                result = alu.or(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
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

    private int parseAddress(String arg) {
        if (arg.startsWith("0x") || arg.startsWith("0X"))
            return Integer.parseInt(arg.substring(2), 16); // hex a decimal
        return Integer.parseInt(arg); // decimal normal
    }

}
