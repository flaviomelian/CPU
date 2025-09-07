import ALU from "../ALU/ALU";
import UC from "../UC/UC";
import Registers from "../Registers/Registers";
import './CPU.css';

const CPU = ({ activeInstruction }) => {
  return (
    <div className="cpu-container">
      <div>
        <ALU operation={activeInstruction}/>
        <UC activeInstruction={activeInstruction} />
      </div>
      <Registers />
    </div>
  );
};

export default CPU;
