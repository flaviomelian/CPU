import express from "express";
import http from "http";
import { Server } from "socket.io";
import cors from "cors";
import axios from "axios";
import router from "./api/routes/index.js";

const app = express();
app.use(cors()).use(express.json()).use("/api", router);

// En vez de app.listen, creamos un servidor HTTP
const server = http.createServer(app);

// Configurar socket.io
const io = new Server(server, {
  cors: {
    origin: "http://localhost:5173", // tu frontend
    methods: ["GET", "POST"]
  }
});

let lastMemory = [];

// Cliente conectado
io.on("connection", async (socket) => {
  console.log("Cliente conectado:", socket.id);

  try {
    // Traer memoria actual desde Spring en el momento de conexión
    const response = await axios.get("http://localhost:8080/memory");
    lastMemory = response.data;
    socket.emit("memoryUpdate", lastMemory); // mandar estado inicial
  } catch (err) {
    console.error("Error obteniendo memoria inicial:", err.message);
  }
});


// Polling interno a Spring (solo en el backend)
setInterval(async () => {
  try {
    const response = await axios.get("http://localhost:8080/memory");
    const newMemory = response.data;

    if (JSON.stringify(newMemory) !== JSON.stringify(lastMemory)) {
      lastMemory = newMemory;
      io.emit("memoryUpdate", newMemory); // notificar a todos los clientes
    }
  } catch (err) {
    console.error("Error obteniendo memoria de Spring:", err.message);
  }
}, 1000);

// Arrancar servidor
const PORT = 3000;
server.listen(PORT, () => {
  console.log(`Servidor escuchando en http://localhost:${PORT}`);
});
