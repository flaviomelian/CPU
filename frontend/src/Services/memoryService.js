import api from './index';

export const getMemory = async () => {
    try {
        const response = await api.get('/state');
        return response.data; // asumimos un array de {address: 0-255, value: 0-255}
    } catch (err) {
        console.error('Error obteniendo memoria:', err);
        return [];
    }  
};

export const updateMemory = async (address, value) => {
    try {
        await api.put('/memory', { address, value });
    } catch (err) {
        console.error('Error actualizando memoria:', err);
    }
};