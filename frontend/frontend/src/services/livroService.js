import api from '../api/api';

export const listarLivrosAPI = async () => {
    const response = await api.get('/livros');
    return response.data; 
};

export const cadastrarLivroAPI = async (livro) => {
    const response = await api.post('/livros', livro);
    return response.data;
};

export const deletarLivroAPI = async (id) => {
    await api.delete(`/livros/${id}`);
};

export const atualizarLivroAPI = async (id, livro) => {
    const response = await api.put(`/livros/${id}`, livro);
    return response.data;
};