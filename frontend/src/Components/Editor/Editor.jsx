import React, { useState } from 'react';
import './Editor.css';
import { uploadRoutine } from '../../Services/routineService';
import { updateMemory } from '../../Services/memoryService';

const Editor = () => {
  const [routine, setRoutine] = useState(`.data
# Aquí van los datos

.text
# Aquí va el código`);

  const handleChange = (e) => {
    setRoutine(e.target.value);
  };

  const handleNew = () => {
    setRoutine(`.data
# Aquí van los datos

.text
# Aquí va el código`);
  };

  const parseDataSection = (dataSection) => {
    const memory = {};
    const lines = dataSection.split('\n');

    lines.forEach(line => {
      const trimmed = line.trim();
      if (!trimmed || trimmed.startsWith('#')) return; // saltar comentarios y líneas vacías

      const [addrPart, bytesPart] = trimmed.split(':');
      if (!addrPart || !bytesPart) return;

      const address = parseInt(addrPart.trim(), 16); // dirección base
      const bytes = bytesPart.trim().split(' ');

      bytes.forEach((b, i) => {
        memory[address + i] = parseInt(b, 16); // convertir cada byte a decimal
      });
    });

    return memory; // objeto {address: valueDecimal, ...}
  };

  const handleUpload = async () => {
    try {
      const dataSectionMatch = routine.match(/\.data([\s\S]*?)\.text/);
      const textSectionMatch = routine.match(/\.text([\s\S]*)/);

      const dataSection = dataSectionMatch ? dataSectionMatch[1].trim() : '';
      const textSection = textSectionMatch ? textSectionMatch[1].trim() : '';

      const memoryData = parseDataSection(dataSection);

      await uploadRoutine({ memory: memoryData, code: textSection });
    } catch (err) {
      console.error('Error cargando rutina:', err);
      alert('Error cargando rutina');
    }
  };

  const getMemoryArray = () => {
    const parts = routine.split(".text");
    const beforeText = parts[0]; // todo lo que está antes de .text
    const dataSection = beforeText.replace(".data", "").trim();
    return dataSection
  }

  return (
    <div id="editor">
      <textarea
        rows="30"
        cols="50"
        value={routine}
        onChange={handleChange}
      />
      <div className='editor-buttons'>
        <button onClick={handleNew}>Nuevo</button>
        <button onClick={() => updateMemory(getMemoryArray())}>Cargar Datos en memoria</button>
        <button>Step Over</button>
      </div>
      <button onClick={handleUpload}>Ensamblar y Cargar</button>
    </div>
  );
};

export default Editor;

