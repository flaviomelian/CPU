package com.flavio.spring.config;

import com.flavio.spring.models.MemoryModel;
import com.flavio.spring.models.RegisterModel;
import com.flavio.spring.repositories.MemoryRepository;
import com.flavio.spring.repositories.RegisterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRegisters(RegisterRepository registerRepository) {
        return args -> {
            // Lista de registros de la CPU
            String[] registers = {
                    "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", // registros generales
                    "ACC", // acumulador
                    "PC", // contador de programa
                    "IR", // registro de instrucción
                    "MAR", // Memory Address Register
                    "MBR", // Memory Buffer Register
                    "FLAG" // flags (cero, carry, etc.)
            };

            for (String reg : registers) {
                if (!registerRepository.existsById(reg)) {
                    RegisterModel newReg = new RegisterModel();
                    newReg.setName(reg);
                    newReg.setValue(0); // todos inicializados en 0
                    registerRepository.save(newReg);
                }
            }

            System.out.println("✅ Registros inicializados en la BD");
        };
    }

    @Bean
    CommandLineRunner initMemory(MemoryRepository memoryRepository) {
        return args -> {
            int memorySize = 256; // por ejemplo 256 celdas
            for (int address = 0; address < memorySize; address++) {
                if (!memoryRepository.existsById(address)) {
                    memoryRepository.save(new MemoryModel(address, 0));
                }
            }
            System.out.println("✅ Memoria inicializada en la BD");
        };
    }

}
