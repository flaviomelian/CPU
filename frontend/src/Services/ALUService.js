import api from './index';

export const getALUState = async () => {
    try {
        const response = await api.get('/register');
        console.log('Registros:', response.data);
        return response.data; // asumimos un array de {address: 0-255, value: 0-255}
    } catch (err) {
        console.error('Error obteniendo memoria:', err);
        return [];
    }  
};