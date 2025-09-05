import React from 'react'
import './Editor.css'

const Editor = () => {
  return (
    <div id='editor'>
      <textarea rows="30" cols="20" placeholder="Escribe tu rutina aqui..."/>
      <div>
        <button>Guardar</button>
        <button>Cargar</button>
        <button>Nuevo</button>
      </div>
      <div>
        <button>Step Over</button>
        <button>Cargar</button>
        <button>Nuevo</button>
      </div>
      <button>Ensamblar y Cargar</button>
    </div>
  )
}

export default Editor
