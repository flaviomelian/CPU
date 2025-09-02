import './Home.css';

const Home = () => {
  return (
    <div className="home-container">
      <h1>Bienvenido al CPU Simulator</h1>
      
      <section className="intro">
        <p>
          Este simulador permite ejecutar instrucciones ensamblador sobre una CPU virtual. 
          Puedes enviar rutinas completas o ejecutar instrucciones individuales y ver cómo 
          afectan a los registros, la memoria y la Unidad de Control (UC).
        </p>
      </section>

      <section className="features">
        <h2>Características principales</h2>
        <ul>
          <li>Soporte completo de instrucciones: MOV, COP, ADD, SUB, MUL, INC, DEC, AND, OR, LD, ST, JMP, JZ.</li>
          <li>Visualización en tiempo real del estado de los registros y la memoria.</li>
          <li>Control del reloj de la CPU con fases FETCH, DECODE y EXECUTE.</li>
          <li>Persistencia de la UC y registros en base de datos para seguimiento histórico.</li>
          <li>Frontend interactivo para enviar rutinas y ver los efectos paso a paso.</li>
        </ul>
      </section>

      <section className="how-to">
        <h2>Cómo usarlo</h2>
        <ol>
          <li>Escribe tu rutina en ensamblador en el editor, se guardará en un archivo de texto (.txt).</li>
          <li>Usa el formulario de subida en la interfaz para enviar la rutina al backend.</li>
          <li>Observa cómo se actualizan los registros, la memoria y la UC en tiempo real.</li>
          <li>Opcional: ejecuta instrucciones individuales usando el endpoint de prueba.</li>
        </ol>
      </section>

      <section className="note">
        <p>
          Este simulador es educativo: muestra cómo funciona un CPU paso a paso, incluyendo 
          la ejecución de instrucciones, flags, y control de flujo. Ideal para aprender arquitectura y microprogramación.
        </p>
      </section>
    </div>
  );
};

export default Home;

