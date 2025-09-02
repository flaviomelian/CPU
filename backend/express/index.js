import express from "express";
import morgan from "morgan";
import apiRouter from "./api/routes/index.js";
import cors from "cors";

const app = express();
const port = 3000;

app.use(morgan("dev"))
    .use(express.json())
    .use(cors({
        origin: 'http://localhost:5173'
    }))
    .use("/api", apiRouter)
    .listen(port, () => console.log(`Express escuchando en puerto ${port}`));
