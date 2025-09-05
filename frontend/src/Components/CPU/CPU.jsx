import ALU from "../ALU/ALU";
import UC from "../UC/UC";
import Registers from "../Registers/Registers";
import './CPU.css';

const CPU = ({ activeInstruction }) => {
  return (
    <div className="cpu-container">
      <ALU />
      <UC activeInstruction={activeInstruction} />
      <Registers />
    </div>
  );
};

export default CPU;
