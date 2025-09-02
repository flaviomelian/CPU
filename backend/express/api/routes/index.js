import { Router } from "express";
import { uploadRoutine, getState } from "../controllers/routine.controller.js";

const router = Router();

router.post("/upload-routine", uploadRoutine);
router.get("/state", getState);

export default router;
