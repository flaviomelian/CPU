import ALU from "../ALU/ALU"
import UC from "../UC/UC"
import Registers from "../Registers/Registers"
import './CPU.css'

const CPU = () => {
  return (
    <div className="cpu-container">
      <ALU />
      <UC />
      <Registers />
    </div>
  )
}

export default CPU
