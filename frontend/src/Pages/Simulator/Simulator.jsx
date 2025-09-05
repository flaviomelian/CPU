import { useState } from 'react';
import Editor from '../../Components/Editor/Editor';
import CPU from '../../Components/CPU/CPU';
import Memory from '../../Components/Memory/Memory';
import Instruction from '../../Components/Instruction/Instruction';
import './Simulator.css';

const Simulator = () => {
  const [currentInstruction, setCurrentInstruction] = useState("");

  const execute = (instruction) => {
    setCurrentInstruction(instruction); // guardo la última ejecutada
  };

  return (
    <div className="simulator">
      <div>
        <Editor />
        <CPU activeInstruction={currentInstruction} />
        <Memory />
      </div>
      <Instruction execute={execute} />
    </div>
  );
};

export default Simulator;
