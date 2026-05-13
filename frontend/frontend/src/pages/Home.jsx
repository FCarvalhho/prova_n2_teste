import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { listarLivrosAPI, cadastrarLivroAPI, deletarLivroAPI, atualizarLivroAPI } from '../services/livroService';
import { criarPedidoAPI, listarPedidosAPI, atualizarStatusPedidoAPI } from '../services/pedidoService';
import styles from './Home.module.css';

const Home = () => {
    const { signOut, role, userId } = useContext(AuthContext);
    const [livros, setLivros] = useState([]);
    const [loading, setLoading] = useState(true);
    const [idEditando, setIdEditando] = useState(null);
    const [livroForm, setLivroForm] = useState({ titulo: '', autor: '', preco: '', categoria: '', estoque: '' });
    const [carrinho, setCarrinho] = useState([]);
    const [pedidos, setPedidos] = useState([]);

    const carregarLivros = async () => {
        try {
            const dados = await listarLivrosAPI();
            setLivros(dados);
            if (role === 'ADMIN') {
                const dadosPedidos = await listarPedidosAPI();
                setPedidos(dadosPedidos);
            }
        } catch (error) {
            console.error("Erro ao buscar livros", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        carregarLivros();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setLivroForm({ ...livroForm, [name]: value });
    };

    const handleSalvar = async (e) => {
        e.preventDefault();
        try {
            if (idEditando) {
                await atualizarLivroAPI(idEditando, livroForm);
                alert("Livro atualizado com sucesso!");
            } else {
                await cadastrarLivroAPI(livroForm);
                alert("Livro cadastrado com sucesso!");
            }
            setLivroForm({ titulo: '', autor: '', preco: '', categoria: '', estoque: '' });
            setIdEditando(null);
            carregarLivros();
        } catch (error) {
            alert("Erro ao salvar livro.");
        }
    };

    const handleEditarClick = (livro) => {
        setLivroForm({ titulo: livro.titulo, autor: livro.autor, preco: livro.preco, categoria: livro.categoria, estoque: livro.estoque });
        setIdEditando(livro.id);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const handleDeletar = async (id) => {
        if (window.confirm("Tem certeza que deseja excluir este livro?")) {
            try {
                await deletarLivroAPI(id);
                carregarLivros();
            } catch (error) {
                alert("Erro ao deletar livro.");
            }
        }
    };

    const cancelarEdicao = () => {
        setLivroForm({ titulo: '', autor: '', preco: '', categoria: '', estoque: '' });
        setIdEditando(null);
    };

    const adicionarAoCarrinho = (livro) => {
        const qtdNoCarrinho = carrinho.filter(item => item.id === livro.id).length;

        if (qtdNoCarrinho >= livro.estoque) {
            alert(`Desculpe, temos apenas ${livro.estoque} unidade(s) de "${livro.titulo}" em estoque!`);
            return;
        }

        setCarrinho([...carrinho, livro]);
    };

    const removerDoCarrinho = (indexParaRemover) => {
        const novoCarrinho = carrinho.filter((_, index) => index !== indexParaRemover);
        setCarrinho(novoCarrinho);
    };

    const finalizarPedido = async () => {
        if (carrinho.length === 0) return;

        const valorTotal = carrinho.reduce((total, livro) => total + parseFloat(livro.preco), 0);

        try {
            await criarPedidoAPI(userId, valorTotal);
            alert(`Pedido finalizado com sucesso! Valor Total: R$ ${valorTotal.toFixed(2)}`);
            setCarrinho([]);
        } catch (error) {
            alert("Erro ao finalizar pedido.");
            console.error(error);
        }
    };

    return (
        <div className={styles.container}>
            <header className={styles.header}>
                <h1>Painel de Livros</h1>
                <button onClick={signOut} className={styles.logoutBtn}>Sair do Sistema</button>
            </header>

            {/* BLOCO DO ADMIN: FORMULÁRIO DE LIVROS */}
            {role === 'ADMIN' && (
                <div className={styles.formContainer}>
                    <h2>{idEditando ? '✏️ Editando Livro' : '➕ Cadastrar Novo Livro'}</h2>
                    <form onSubmit={handleSalvar}>
                        <div className={styles.formGrid}>
                            <div className={styles.formGroup}><label>Título</label><input type="text" name="titulo" value={livroForm.titulo} onChange={handleInputChange} required /></div>
                            <div className={styles.formGroup}><label>Autor</label><input type="text" name="autor" value={livroForm.autor} onChange={handleInputChange} required /></div>
                            <div className={styles.formGroup}><label>Categoria</label><input type="text" name="categoria" value={livroForm.categoria} onChange={handleInputChange} required /></div>
                            <div className={styles.formGroup}><label>Preço (R$)</label><input type="number" step="0.01" name="preco" value={livroForm.preco} onChange={handleInputChange} required /></div>
                            <div className={styles.formGroup}><label>Estoque (un.)</label><input type="number" name="estoque" value={livroForm.estoque} onChange={handleInputChange} required /></div>
                        </div>
                        <div style={{ display: 'flex', gap: '1rem' }}>
                            <button type="submit" className={styles.submitBtn}>{idEditando ? 'Atualizar Livro' : 'Salvar Livro'}</button>
                            {idEditando && <button type="button" onClick={cancelarEdicao} style={{ padding: '0.75rem', cursor: 'pointer' }}>Cancelar</button>}
                        </div>
                    </form>
                </div>
            )}

            {/* BLOCO DO ADMIN: TABELA DE PEDIDOS (Estava faltando isso!) */}
            {role === 'ADMIN' && (
                <div className={styles.formContainer} style={{ marginTop: '2rem' }}>
                    <h2>📦 Gerenciar Pedidos Recentes</h2>
                    {pedidos.length === 0 ? (
                        <p>Nenhum pedido recebido ainda.</p>
                    ) : (
                        <table style={{ width: '100%', textAlign: 'left', borderCollapse: 'collapse' }}>
                            <thead>
                                <tr style={{ borderBottom: '2px solid #ddd' }}>
                                    <th>ID</th>
                                    <th>Data</th>
                                    <th>Valor</th>
                                    <th>Status</th>
                                    <th>Ação</th>
                                </tr>
                            </thead>
                            <tbody>
                                {pedidos.map(pedido => (
                                    <tr key={pedido.id} style={{ borderBottom: '1px solid #eee' }}>
                                        <td style={{ padding: '0.5rem 0' }}>#{pedido.id}</td>
                                        <td>{new Date(pedido.dataPedido).toLocaleDateString('pt-BR')}</td>
                                        <td>R$ {pedido.valorTotal.toFixed(2)}</td>
                                        <td>
                                            <span style={{ 
                                                backgroundColor: pedido.status === 'PENDENTE' ? '#ffc107' : '#28a745',
                                                padding: '0.2rem 0.5rem', borderRadius: '4px', color: pedido.status === 'PENDENTE' ? 'black' : 'white', fontSize: '0.85rem', fontWeight: 'bold'
                                            }}>
                                                {pedido.status}
                                            </span>
                                        </td>
                                        <td>
                                            {pedido.status === 'PENDENTE' && (
                                                <button 
                                                    onClick={async () => {
                                                        await atualizarStatusPedidoAPI(pedido.id, 'PAGO');
                                                        const att = await listarPedidosAPI();
                                                        setPedidos(att);
                                                    }}
                                                    style={{ backgroundColor: '#28a745', color: 'white', border: 'none', padding: '0.3rem 0.5rem', borderRadius: '4px', cursor: 'pointer' }}
                                                >
                                                    Aprovar Pagamento
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                </div>
            )}

            {/* BLOCO DO CLIENTE: CARRINHO */}
            {role === 'CLIENTE' && carrinho.length > 0 && (
                <div style={{ backgroundColor: '#e9ecef', padding: '1rem', borderRadius: '8px', marginBottom: '2rem' }}>
                    <h2>🛒 Meu Carrinho</h2>
                    <ul style={{ listStyle: 'none', padding: 0 }}>
                        {carrinho.map((livro, index) => (
                            <li key={index} style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem', borderBottom: '1px solid #ccc', paddingBottom: '0.5rem' }}>
                                <span>{livro.titulo}</span>
                                <span>
                                    R$ {livro.preco}
                                    <button onClick={() => removerDoCarrinho(index)} style={{ marginLeft: '1rem', color: 'red', border: 'none', background: 'none', cursor: 'pointer' }}>Remover</button>
                                </span>
                            </li>
                        ))}
                    </ul>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '1rem' }}>
                        <strong>Total: R$ {carrinho.reduce((acc, curr) => acc + parseFloat(curr.preco), 0).toFixed(2)}</strong>
                        <button onClick={finalizarPedido} className={styles.submitBtn} style={{ width: 'auto' }}>Finalizar Pedido</button>
                    </div>
                </div>
            )}

            {/* LISTAGEM DE LIVROS PARA TODOS */}
            <main>
                <h2>Acervo Disponível</h2>
                {loading ? (
                    <p>Carregando livros...</p>
                ) : (
                    <div className={styles.grid}>
                        {livros.length === 0 ? (
                            <div className={styles.emptyState}><p>Nenhum livro cadastrado ainda.</p></div>
                        ) : (
                            livros.map((livro) => (
                                <div key={livro.id} className={styles.card}>
                                    {role === 'ADMIN' && (
                                        <div className={styles.cardActions}>
                                            <button className={styles.editBtn} onClick={() => handleEditarClick(livro)}>✎</button>
                                            <button className={styles.deleteBtn} onClick={() => handleDeletar(livro.id)}>X</button>
                                        </div>
                                    )}

                                    <h3>{livro.titulo}</h3>
                                    <p><strong>Autor:</strong> {livro.autor}</p>
                                    <p><strong>Categoria:</strong> {livro.categoria}</p>
                                    <p><strong>Estoque:</strong> {livro.estoque} un.</p>
                                    <div className={styles.price}>
                                        {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(livro.preco)}
                                    </div>

                                    {role === 'CLIENTE' && (
                                        <button
                                            onClick={() => adicionarAoCarrinho(livro)}
                                            style={{ width: '100%', padding: '0.5rem', backgroundColor: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', marginTop: '1rem' }}
                                        >
                                            Adicionar ao Carrinho
                                        </button>
                                    )}
                                </div>
                            ))
                        )}
                    </div>
                )}
            </main>
        </div>
    );
};

export default Home;