import React from 'react';
import './Instruction.css';

const Instruction = ({ execute }) => {
  return (
    <input
      type="text"
      placeholder="Ejecutar instrucción de manera individual"
      onKeyDown={(e) => {
        if (e.key === "Enter") {
          console.log("Instrucción:", e.target.value);
          execute(e.target.value); // ya no es execute.execute
          e.target.value = ""; // limpiar input
        }
      }}
    />
  );
};

export default Instruction;
