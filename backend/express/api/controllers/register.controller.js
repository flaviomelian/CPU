import axios from "axios";

const getRegisters = async (req, res) => {
  try {
    const response = await axios.get("http://localhost:8080/register");
    res.json(response.data);
  } catch (err) {
    res.status(500).json({ error: "Error obteniendo estado desde Spring", details: err.message });
  }
};

export { getRegisters };