package com.flavio.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flavio.spring.models.MemoryModel;
import com.flavio.spring.models.PCModel;
import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.models.UCModel;
import com.flavio.spring.repositories.PCRepository;
import com.flavio.spring.repositories.RegisterRepository;
import com.flavio.spring.repositories.UCRepository;

import java.util.Objects;

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

    @Autowired
    private UCRepository ucRepository;

    private int ucSnapshotCounter = 1; // para IDs únicos si guardas un snapshot por instrucción

    private static final String PC_ID = "PC";

    // ======= Estado de la CPU / Reloj =======
    private final CpuState cpuState = new CpuState();

    @FunctionalInterface
    public interface InstructionDecoder {
        /**
         * Decodifica el valor del IR a una instrucción legible, ej: "ADD R1 R2 R3".
         * Lanza IllegalStateException si no puede decodificar.
         */
        String decode(int irValue);
    }

    // Por defecto, si no inyectas un decoder, avisa claramente.
    private InstructionDecoder decoder = ir -> {
        throw new IllegalStateException("InstructionDecoder no configurado; no puedo decodificar IR=" + ir);
    };

    public void setInstructionDecoder(InstructionDecoder decoder) {
        this.decoder = Objects.requireNonNull(decoder);
    }

    // ======= PC helpers =======
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

    // ======= FLAGS =======
    private boolean zeroFlag = false;
    private boolean carryFlag = false;

    private void updateFlags(int result) {
        zeroFlag = (result & 0xFF) == 0;
        // carry aquí es “overflow fuera de 8 bits” en operaciones no enmascaradas
        carryFlag = (result > 0xFF || result < 0);
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public boolean isCarryFlag() {
        return carryFlag;
    }

    // ======= Núcleo de ejecución ISA =======
    public void execute(String instruction, String arg1, String arg2, String arg3) {
        if (instruction == null || instruction.isBlank()) {
            throw new IllegalArgumentException("Instrucción vacía o nula");
        }

        RegisterModel result;
        RegisterModel dest, src;

        switch (instruction.toUpperCase()) {
            case "MOV":
                ensureArgs(instruction, arg1, arg2);
                src = readRegister(arg1);
                dest = readRegister(arg2);
                dest.setValue(src.getValue() & 0xFF);
                writeRegister(dest);
                src.setValue(0);
                writeRegister(src);
                persistUCState(false, false, true, "MOV"); // ejecución ALU/UC
                break;

            case "COP":
                ensureArgs(instruction, arg1, arg2);
                src = readRegister(arg1);
                dest = readRegister(arg2);
                dest.setValue(src.getValue() & 0xFF);
                writeRegister(dest);
                persistUCState(false, false, true, "COP");
                break;

            case "ADD":
                ensureArgs3(instruction, arg1, arg2, arg3);
                result = alu.add(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "ADD");
                break;

            case "SUB":
                ensureArgs3(instruction, arg1, arg2, arg3);
                result = alu.sub(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "SUB");
                break;

            case "MUL":
                ensureArgs3(instruction, arg1, arg2, arg3);
                result = alu.mul(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "MUL");
                break;

            case "INC":
                ensureArgs(instruction, arg1, null);
                result = alu.inc(readRegister(arg1), readRegister(arg1));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "INC");
                break;

            case "DEC":
                ensureArgs(instruction, arg1, null);
                result = alu.dec(readRegister(arg1), readRegister(arg1));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "DEC");
                break;

            case "AND":
                ensureArgs3(instruction, arg1, arg2, arg3);
                result = alu.and(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "AND");
                break;

            case "OR":
                ensureArgs3(instruction, arg1, arg2, arg3);
                result = alu.or(readRegister(arg1), readRegister(arg2), readRegister(arg3));
                writeRegister(result);
                updateFlags(result.getValue());
                persistUCState(false, false, true, "OR");
                break;

            case "JMP":
                ensureArgs(instruction, arg1, null);
                setPC(parseAddress(arg1));
                persistUCState(false, false, true, "JMP");
                return;

            case "JZ":
                ensureArgs(instruction, arg1, arg2);
                RegisterModel isZero = readRegister(arg1);
                if (isZero.getValue() == 0) {
                    setPC(parseAddressRequireHex(arg2, "JZ requiere una dirección 0xNN"));
                    persistUCState(false, false, true, "JZ");
                    return;
                }
                persistUCState(false, false, true, "JZ");
                break;

            case "LD":
                ensureArgs(instruction, arg1, arg2);
                dest = readRegister(arg1);
                int address = parseAddress(arg2);
                MemoryModel mem = memoryService.getMemoryById(address)
                        .orElseThrow(() -> new IllegalArgumentException("Dirección inválida: " + arg2));
                dest.setValue(mem.getValue() & 0xFF);
                writeRegister(dest);
                persistUCState(true, false, false, null); // lectura de memoria
                break;

            case "ST":
                ensureArgs(instruction, arg1, arg2);
                src = readRegister(arg1);
                int addr = parseAddress(arg2);
                memoryService.writeMemory(addr, src.getValue() & 0xFF);
                persistUCState(false, true, false, null); // escritura en memoria
                break;

            default:
                throw new IllegalArgumentException("Instrucción no soportada: " + instruction);
        }

        // Avance por defecto del PC si no hubo salto
        incrementPC();
    }

    // ======= Helpers de argumentos =======
    private static void ensureArgs(String instr, String a1, String a2) {
        if (a1 == null || a1.isBlank() || (a2 != null && a2.isBlank())) {
            throw new IllegalArgumentException(instr + " argumentos inválidos");
        }
    }

    private static void ensureArgs3(String instr, String a1, String a2, String a3) {
        if (a1 == null || a2 == null || a3 == null ||
                a1.isBlank() || a2.isBlank() || a3.isBlank()) {
            throw new IllegalArgumentException(
                    instr + " requiere 3 argumentos: dest, src1, src2 (o el orden que definas)");
        }
    }

    // ======= Acceso a registros =======
    public RegisterModel readRegister(String name) {
        return registerRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado: " + name));
    }

    public void writeRegister(RegisterModel reg) {
        reg.setValue(reg.getValue() & 0xFF);
        registerRepository.save(reg);
    }

    // ======= Parsing de direcciones =======
    private int parseAddress(String arg) {
        if (arg == null || arg.isBlank())
            throw new IllegalArgumentException("Dirección vacía");

        String s = arg.trim();
        if (s.startsWith("0x") || s.startsWith("0X"))
            return Integer.parseInt(s.substring(2), 16) & 0xFF;

        return Integer.parseInt(s) & 0xFF;
    }

    private int parseAddressRequireHex(String arg, String msgIfNotHex) {
        if (arg == null || arg.isBlank())
            throw new IllegalArgumentException("Dirección vacía");

        String s = arg.trim();
        if (!(s.startsWith("0x") || s.startsWith("0X")))
            throw new IllegalArgumentException(msgIfNotHex);

        return (Integer.parseInt(s.substring(2), 16) & 0xFF);
    }

    // ======= Ciclo de reloj =======
    public void tick() {
        switch (cpuState.getPhase()) {
            case FETCH: {
                persistUCState(true, false, false, null); // leyendo memoria
                // PC -> MAR
                RegisterModel pc = readRegister("PC");
                RegisterModel mar = readRegister("MAR");
                mar.setValue(pc.getValue() & 0xFF);
                writeRegister(mar);

                // MAR -> memoria -> MBR
                MemoryModel mem = memoryService.getMemoryById(mar.getValue() & 0xFF)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Dirección inválida en FETCH: " + mar.getValue()));
                RegisterModel mbr = readRegister("MBR");
                mbr.setValue(mem.getValue() & 0xFF);
                writeRegister(mbr);

                // MBR -> IR
                RegisterModel ir = readRegister("IR");
                ir.setValue(mbr.getValue() & 0xFF);
                writeRegister(ir);

                // PC++
                pc.setValue((pc.getValue() + 1) & 0xFF);
                writeRegister(pc);

                cpuState.setPhase(Phase.DECODE);
                break;
            }

            case DECODE: {
                int irValue = readRegister("IR").getValue() & 0xFF;
                String decoded = decoder.decode(irValue); // <- inyecta tu decoder real
                cpuState.setCurrentInstruction(decoded);
                cpuState.setPhase(Phase.EXECUTE);
                break;
            }

            case EXECUTE: {
                String instr = cpuState.getCurrentInstruction();
                if (instr == null || instr.isBlank()) {
                    throw new IllegalStateException("No hay instrucción decodificada para ejecutar");
                }
                String currentOp = instr.split(" ")[0];
                persistUCState(false, false, true, currentOp); // ejecutando ALU
                String[] parts = instr.trim().split("\\s+");
                String op = parts[0];
                String a1 = parts.length > 1 ? parts[1] : null;
                String a2 = parts.length > 2 ? parts[2] : null;
                String a3 = parts.length > 3 ? parts[3] : null;

                // Ejecuta usando tu mismo motor ISA
                this.execute(op, a1, a2, a3);

                cpuState.setPhase(Phase.FETCH);
                break;
            }
        }
    }

    private void persistUCState(boolean read, boolean write, boolean execute, String aluOp) {
        UCModel uc = new UCModel();
        uc.setId(ucSnapshotCounter++);
        uc.setRead(read);
        uc.setWrite(write);
        uc.setExecute(execute);
        uc.setAluOp(aluOp);
        ucRepository.save(uc);
    }

    // ======= Tipos auxiliares =======
    public enum Phase {
        FETCH, DECODE, EXECUTE
    }

    public static class CpuState {
        private Phase phase = Phase.FETCH;
        private String currentInstruction;
        private int step;

        public Phase getPhase() {
            return phase;
        }

        public void setPhase(Phase phase) {
            this.phase = phase;
        }

        public String getCurrentInstruction() {
            return currentInstruction;
        }

        public void setCurrentInstruction(String instr) {
            this.currentInstruction = instr;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }
    }
}
