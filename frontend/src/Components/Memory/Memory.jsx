import React, { useEffect, useState } from 'react';
import './Memory.css';
import { getMemory } from '../../Services/memoryService';

const Memory = () => {
  const [memory, setMemory] = useState([]);

  // Función para obtener el estado de la memoria desde el backend
  const fetchMemory = async () => {
    try {
      const response = await getMemory(); // endpoint que devuelve la memoria
      setMemory(Array.isArray(response.data) ? response.data : []);
    } catch (err) {
      console.error('Error obteniendo memoria:', err);
    }
  };

  useEffect(() => {
    fetchMemory();
    const interval = setInterval(fetchMemory, 1000); // refresco cada segundo
    return () => clearInterval(interval);
  }, []);

  const renderMemoryCells = () => {
    const rows = [];
    for (let i = 0; i < 16; i++) {
      const cells = [];
      for (let j = 0; j < 16; j++) {
        const address = i * 16 + j;
        const memCell = memory.find((m) => m.address === address);
        const value = memCell ? memCell.value : 0;
        cells.push(
          <td key={j} className="memory-cell">
            {value.toString(16).padStart(2, '0').toUpperCase()}
          </td>
        );
      }
      rows.push(
        <tr key={i}>
          <th>{(i * 16).toString(16).padStart(2, '0').toUpperCase()}</th>
          {cells}
        </tr>
      );
    }
    return rows;
  };

  return (
    <div id="memory-container">
      <h2>Memoria de la CPU</h2>
      <table className="memory-table">
        <thead>
          <tr>
            <th>Addr</th>
            {Array.from({ length: 16 }).map((_, i) => (
              <th key={i}>{i.toString(16).toUpperCase()}</th>
            ))}
          </tr>
        </thead>
        <tbody>{renderMemoryCells()}</tbody>
      </table>
    </div>
  );
};

export default Memory;

