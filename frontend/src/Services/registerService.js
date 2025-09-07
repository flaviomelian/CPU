import api from './index';

export const getRegisters = async () => {
    try {
        const response = await api.get('/register');
        return response.data; // asumimos un array de {address: 0-255, value: 0-255}
    } catch (err) {
        console.error('Error obteniendo memoria:', err);
        return [];
    }  
};

export const clearRegisters = async () => {
    try {
        const response = await api.post('/register/clear');
        return response.data; // asumimos un array de {address: 0-255, value: 0-255}
    } catch (err) {
        console.error('Error limpiando registros:', err);
        return [];
    }
};