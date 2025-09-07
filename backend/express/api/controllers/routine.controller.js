import fs from "fs";
import path from "path";
import { fileURLToPath } from 'url';
import axios from "axios";

// Equivalente a __dirname en ES Modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const uploadRoutine = async (req, res) => {
  const { routine } = req.body;

  if (!routine) {
    return res.status(400).json({ error: "No se proporcionó la rutina" });
  }

  // Ruta del archivo temporal
  const tempFile = path.join(__dirname, "../../temp_routine.txt");

  try {
    // Escribir la rutina en el archivo temporal
    fs.writeFileSync(tempFile, routine, { encoding: "utf-8" });

    // Enviar a Spring
    const response = await axios.post("http://localhost:8080/execute-routine", {
      filePath: tempFile
    });

    res.json({ message: "Rutina enviada a Spring", springResponse: response.data });
  } catch (err) {
    console.error("Error en uploadRoutine:", err.response?.data || err.message);
    res.status(500).json({ error: "Error comunicando con Spring", details: err.message });
  }
};

const getState = async (req, res) => {
  try {
    const response = await axios.get("http://localhost:8080/memory");
    console.log('Estado obtenido desde Spring:', response.data);
    res.json(response.data);
  } catch (err) {
    res.status(500).json({ error: "Error obteniendo estado desde Spring", details: err.message });
  }
};

const execute = async (req, res) => {
  const { instruction } = req.body;
  try {
    const response = await axios.post("http://localhost:8080/uc/execute", { instruction });
    console.log('Instrucción ejecutada en Spring:', response.data);
    res.json(response.data);
  } catch (err) {
    res.status(500).json({ error: "Error ejecutando instrucción en Spring", details: err.message });
  }
};

const loadMemory = async (req, res) => {
  const { address, value } = req.body;
  try {
    const response = await axios.put(
      `http://localhost:8080/memory/${address}`,
      { value } // el body solo necesita el valor
    );
    console.log('Memoria actualizada en Spring:', response.data);
    res.json(response.data);
  } catch (err) {
    console.error("Error actualizando memoria:", err.message);
    res.status(500).json({
      error: "Error actualizando memoria en Spring",
      details: err.message,
    });
  }
};


export { uploadRoutine, getState, execute, loadMemory };