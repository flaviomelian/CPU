import express from "express";
import morgan from "morgan";
import apiRouter from "./api/routes/index.js";

const app = express();
const port = 3000;

app.use(morgan("dev"));
app.use(express.json());
app.use("/api", apiRouter);

app.listen(port, () => console.log(`Express escuchando en puerto ${port}`));
