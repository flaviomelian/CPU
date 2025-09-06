import { Router } from "express";
import { uploadRoutine, getState, execute } from "../controllers/routine.controller.js";
import { getRegisters } from "../controllers/register.controller.js";

const router = Router();

router.post("/upload-routine", uploadRoutine);
router.get("/state", getState);
router.get("/register", getRegisters);
router.post("/uc/execute", execute);

export default router;
