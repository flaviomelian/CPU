import "./UC.css";
import { useEffect, useState } from "react";
import { executeInstruction } from "../../Services/UCService";

const stages = ["FETCH", "DECODE", "EXECUTE", "WRITE BACK"];

const UC = ({ activeInstruction }) => {
  const [stageIndex, setStageIndex] = useState(null);
  const [slots, setSlots] = useState(stages.map(() => null));

  useEffect(() => {
    if (!activeInstruction) return;

    let step = 0;
    setStageIndex(0);
    setSlots(stages.map((_, i) => (i === 0 ? activeInstruction : null)));

    const interval = setInterval(() => {
      step++;
      if (step < stages.length) {
        setStageIndex(step);
        setSlots(stages.map((_, i) => (i === step ? activeInstruction : null)));
      } else {
        clearInterval(interval);

        // 🔥 Aquí es donde realmente ejecutas en el backend
        executeInstruction(activeInstruction);

        // limpiar UI
        setStageIndex(null);
        setSlots(stages.map(() => null));
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [activeInstruction]);

  return (
    <div id="uc-container">
      <h2 className="uc-title">Unidad de Control</h2>
      <div className="uc-stages">
        {stages.map((s, i) => (
          <div key={s} className={`uc-stage ${i === stageIndex ? "active" : ""}`}>
            <p>{s} <strong>{slots[i] || ""}</strong></p>
             
          </div>
        ))}
      </div>
    </div>
  );
};

export default UC;
