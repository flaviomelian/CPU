import React from 'react'
import './Header.css'
import home from '../../assets/home.png'
import cpu from '../../assets/cpu.png'
import { useNavigate } from 'react-router-dom'

const Header = () => {
  const navigate = useNavigate()
  return (
    <div className='header'>
      <img src={home} alt="Home" onClick={() => navigate("/")}/>
      <img src={cpu} alt="CPU"  onClick={() => navigate("/simulator")}/>
    </div>
  )
}

export default Header
