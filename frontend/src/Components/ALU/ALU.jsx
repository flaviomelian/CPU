import React, { useState, useEffect } from 'react';
import './ALU.css';
import { getALUState } from '../../Services/ALUService'; // endpoint que devuelve la última operación

const ALU = () => {
  const operations = ['ADD', 'SUB', 'MUL', 'AND', 'OR', 'NOT', 'INC', 'DEC'];
  const [activeOp, setActiveOp] = useState(null);

  useEffect(() => {
    const fetchALU = async () => {
      try {
        const op = await getALUState(); // devuelve algo como {operation: 'ADD'}
        setActiveOp(op.operation);
      } catch (err) {
        console.error('Error obteniendo estado ALU:', err);
      }
    };

    fetchALU();
    const interval = setInterval(fetchALU, 500); // refresco cada medio segundo
    return () => clearInterval(interval);
  }, []);

  return (
    <div id="alu-container">
      {operations.map((op) => (
        <div
          key={op}
          className={`alu-op ${activeOp === op ? 'active' : ''}`}
        >
          {op}
        </div>
      ))}
    </div>
  );
};

export default ALU;

