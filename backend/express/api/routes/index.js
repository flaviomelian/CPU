import { Router } from "express";
import { uploadRoutine, getState } from "../controllers/routine.controller.js";
import { getRegisters } from "../controllers/register.controller.js";

const router = Router();

router.post("/upload-routine", uploadRoutine);
router.get("/state", getState);
router.get("/register", getRegisters);

export default router;
