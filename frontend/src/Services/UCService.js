import api from './index';

export const getUCState = async () => {
    try {
        const response = await api.get('/uc');
        console.log('Registros:', response.data);
        return response.data; // asumimos un array de {address: 0-255, value: 0-255}
    } catch (err) {
        console.error('Error obteniendo memoria:', err);
        return [];
    }  
};

export const executeInstruction = async (instruction) => {
    try {
        const response = await api.post('/uc/execute', { instruction });
        console.log('Instrucción ejecutada:', response.data);
        return response.data; // respuesta del servidor
    } catch (err) {
        console.error('Error ejecutando instrucción:', err);
        return null;
    }
};