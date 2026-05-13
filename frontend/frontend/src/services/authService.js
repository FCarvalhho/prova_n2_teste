import api from '../api/api';

export const loginAPI = async (email, senha) => {
    const response = await api.post('/login', { email, senha });
    return response.data;
};