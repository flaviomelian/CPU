import { Router } from "express";
import { uploadRoutine, getState, execute, loadMemory } from "../controllers/routine.controller.js";
import { getRegisters, clearRegisters } from "../controllers/register.controller.js";

const router = Router();

router.post("/upload-routine", uploadRoutine);
router.get("/state", getState);
router.get("/register", getRegisters);
router.post("/register/clear", clearRegisters);
router.post("/uc/execute", execute);
router.put("/memory", loadMemory);

export default router;
