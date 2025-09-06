import React from 'react'
import './Editor.css'
import { uploadRoutine } from '../../Services/routineService'

const Editor = () => {
  return (
    <div id='editor'>
      <textarea rows="30" cols="20" placeholder="Escribe tu rutina aqui..."/>
      <div className='editor-buttons'>
        <button>Guardar</button>
        <button>Cargar</button>
        <button>Nuevo</button><button>Step Over</button>
      </div>
      <button onClick={() => uploadRoutine}>Ensamblar y Cargar</button>
    </div>
  )
}

export default Editor
