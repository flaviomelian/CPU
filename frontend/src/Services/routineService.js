import api from './index';

export const uploadRoutine = async (routine) => {
  try {
    const response = await api.post('/upload-routine', routine);
    return response.data; // respuesta del servidor
  } catch (err) {
    console.error('Error cargando rutina:', err);
    return null;
  }
};