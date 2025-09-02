const fs = require("fs");
const path = require("path");
const axios = require("axios");

const uploadRoutine = async (req, res) => {
  const { routine } = req.body;
  const tempFile = path.join(__dirname, "../../temp_routine.txt"); // archivo temporal

  try {
    fs.writeFileSync(tempFile, routine);

    // enviar a Spring
    const response = await axios.post("http://localhost:8080/execute-routine", { filePath: tempFile });

    res.json({ message: "Rutina enviada a Spring", springResponse: response.data });
  } catch (err) {
    res.status(500).json({ error: "Error comunicando con Spring", details: err.message });
  }
};

const getState = async (req, res) => {
  try {
    const response = await axios.get("http://localhost:8080/state");
    res.json(response.data);
  } catch (err) {
    res.status(500).json({ error: "Error obteniendo estado desde Spring", details: err.message });
  }
};

export {uploadRoutine, getState};