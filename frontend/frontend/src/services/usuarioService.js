import api from '../api/api';

export const cadastrarUsuarioAPI = async (nome, email, senha) => {
    const response = await api.post('/usuarios', {
        nome,
        email,
        senha,
        role: 'CLIENTE' 
    });
    return response.data;
};