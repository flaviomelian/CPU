import "./UC.css";
import { useEffect } from "react";
import { executeInstruction } from "../../Services/UCService";

const UC = ({ activeInstruction }) => {

  useEffect(() => {
    if (activeInstruction) executeInstruction(activeInstruction)
  }, [activeInstruction]);

  return (
    <div id="uc-container">
      <h2 className="uc-title">Unidad de Control</h2>
      <p className="uc-instr">
        Instrucción actual: <strong>{activeInstruction || "N/A"}</strong>
      </p>
    </div>
  );
};

export default UC;
