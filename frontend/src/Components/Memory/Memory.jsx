import React, { useEffect, useState } from "react";
import { io } from "socket.io-client";
import "./Memory.css";

const socket = io("http://localhost:3000");

const Memory = () => {
  const [memory, setMemory] = useState([]);

  useEffect(() => {
    // Escuchar los eventos del backend
    socket.on("memoryUpdate", (data) => {
      console.log("Nueva memoria recibida:", data);
      setMemory(data);
    });

    // Limpiar listener al desmontar
    return () => {
      socket.off("memoryUpdate");
    };
  }, []);

  const renderMemoryCells = () => {
    const rows = [];

    for (let i = 0; i < 16; i++) { // 16 filas de alto
      const cells = [];

      for (let j = 0; j < 16; j++) { // 16 columnas de ancho
        const address = i * 16 + j;
        const memCell = memory.find((m) => m.address === address);
        const valueHex = memCell ? memCell.hexValue : '0x00';

        // Se aplica clase si el valor no es 0x00
        const isUsed = valueHex !== '0x00';

        cells.push(
          <td
            key={j}
            className={`memory-cell ${isUsed ? 'used' : ''}`}
          >
            {valueHex}
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
