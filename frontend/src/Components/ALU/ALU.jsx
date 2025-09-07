import React, { useState, useEffect } from 'react';
import './ALU.css';
import { getALUState } from '../../Services/ALUService'; // endpoint que devuelve la última operación

const ALU = ({ operation }) => {
  const operations = ['ADD', 'SUB', 'MUL', 'AND', 'OR', 'NOT', 'INC', 'DEC'];
  const [activeOp, setActiveOp] = useState(operation || null);

  useEffect(() => {
    const fetchALU = async () => {
      try {
        const { operation: op } = await getALUState();
        setActiveOp(op); // aquí sí usamos lo que viene del servicio
      } catch (err) {
        console.error('Error obteniendo estado ALU:', err);
      }
    };

    fetchALU();
    const interval = setInterval(fetchALU, 500);
    return () => clearInterval(interval);
  }, []); // solo al montar


  return (
    <div id="alu-container">
      <h3 className='alu'>ALU</h3>
      <div id='operations'>
        {operations.map((op) => (
          <div
            key={op}
            className={`alu-op ${activeOp === op ? 'active' : ''}`}
          >
            {op}
          </div>
        ))}
      </div>
    </div>
  );
};

export default ALU;

