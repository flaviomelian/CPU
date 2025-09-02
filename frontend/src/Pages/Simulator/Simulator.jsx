import Editor from '../../Components/Editor/Editor'
import CPU from '../../Components/CPU/CPU'
import Memory from '../../Components/Memory/Memory'
import Instruction from '../../Components/Instruction/Instruction'
import './Simulator.css'

const Simulator = () => {
    return (
        <div className='simulator'>
            <div>
                <Editor />
                <CPU />
                <Memory />
            </div>
            <Instruction/>
        </div>
    )
}

export default Simulator
