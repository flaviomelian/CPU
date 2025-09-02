import { createBrowserRouter } from 'react-router-dom';
import Root from '../App.jsx';  // Importa el componente principal que actúa como contenedor de las rutas hijas.
import Home from '../Pages/Home/Home.jsx';  // Importa el componente de la página de inicio.
import Simulator from '../Pages/Simulator/Simulator.jsx'; 

const router = createBrowserRouter([  // Se crea el enrutador utilizando createBrowserRouter.
  {
    path: '',  // La ruta base de la aplicación.
    element: <Root />,  // El componente principal que actúa como contenedor de las rutas hijas.
    children: [  // Las rutas hijas definidas dentro de la ruta principal.
      {
        path: '',  // Ruta para la página de inicio.
        element: <Home />,  // Componente que se renderiza en la ruta de inicio.
      },
      {
        path: '/simulator',  // Ruta para la página de inicio.
        element: <Simulator />,  // Componente que se renderiza en la ruta de inicio.
      }
    ],
  },
]);

// Se exporta la configuración de las rutas para que se pueda utilizar en el componente principal (App).
export default router;