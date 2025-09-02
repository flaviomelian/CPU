import React from 'react'
import './Editor.css'

const Editor = () => {
  return (
    <div id='editor'>
      <textarea rows="40" cols="20" placeholder="Escribe tu rutina aqui..."/>
      <button>Ensamblar y Cargar</button>
    </div>
  )
}

export default Editor
