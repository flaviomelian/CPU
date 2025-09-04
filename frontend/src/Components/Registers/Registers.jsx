import React, {useState, useEffect} from 'react'
import { getRegisters } from '../../Services/registerService'
import './Registers.css'

const Registers = () => {

  const [registers, setRegisters] = useState([])

  useEffect(() => {
    const fetchRegisters = async () => {
      const data = await getRegisters()
      setRegisters(data)
    }
    fetchRegisters()
  }, [])

  return (
    <div>
      <table className='registers-table'>
        <thead className='registers-header'>
          <tr className='registers-row'>
            <th className='head reg'>Register</th>
            <th className='head val'>Value</th>
          </tr>
        </thead>
        <tbody>
          {
            registers.map((reg, index) => (
              <tr key={index}>
                <td className='name'>{reg.name}</td>
                <td className='value'>{reg.value}</td>
              </tr>
            )) 
          }
        </tbody>
      </table>
    </div>
  )
}

export default Registers
