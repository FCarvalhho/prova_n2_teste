import api from '../api/api';

export const criarPedidoAPI = async (clienteId, valorTotal) => {
    const response = await api.post('/pedidos', {
        clienteId,
        valorTotal
    });
    return response.data;
};

export const listarPedidosAPI = async () => {
    const response = await api.get('/pedidos');
    return response.data;
};

export const atualizarStatusPedidoAPI = async (id, status) => {
    const response = await api.put(`/pedidos/${id}/status?status=${status}`);
    return response.data;
};