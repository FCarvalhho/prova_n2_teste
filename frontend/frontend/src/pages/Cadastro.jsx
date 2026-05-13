import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { cadastrarUsuarioAPI } from '../services/usuarioService';
import styles from './Login.module.css'; 

const Cadastro = () => {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [sucesso, setSucesso] = useState(false);
    
    const navigate = useNavigate();

    const handleCadastro = async (e) => {
        e.preventDefault();
        setErro('');
        setSucesso(false);

        if (!nome || !email || !senha) {
            setErro('Por favor, preencha todos os campos.');
            return;
        }

        try {
            await cadastrarUsuarioAPI(nome, email, senha);
            setSucesso(true);
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (error) {
            if (error.response && error.response.data && error.response.data.erro) {
                setErro(error.response.data.erro);
            } else {
                setErro('Erro ao cadastrar. Tente novamente.');
            }
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.formBox}>
                <h2>Criar Conta</h2>
                
                {erro && <div className={styles.error}>{erro}</div>}
                {sucesso && <div style={{ color: 'green', textAlign: 'center', marginBottom: '1rem' }}>Cadastro realizado com sucesso! Redirecionando...</div>}
                
                <form onSubmit={handleCadastro}>
                    <div className={styles.inputGroup}>
                        <label>Nome</label>
                        <input 
                            type="text" 
                            value={nome} 
                            onChange={(e) => setNome(e.target.value)} 
                            placeholder="Digite seu nome completo"
                        />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>E-mail</label>
                        <input 
                            type="email" 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            placeholder="Digite seu e-mail"
                        />
                    </div>
                    <div className={styles.inputGroup}>
                        <label>Senha</label>
                        <input 
                            type="password" 
                            value={senha} 
                            onChange={(e) => setSenha(e.target.value)} 
                            placeholder="Crie uma senha (mínimo 6 caracteres)"
                        />
                    </div>
                    <button type="submit" className={styles.button}>Cadastrar</button>
                </form>
                
                <div style={{ textAlign: 'center', marginTop: '1rem' }}>
                    <Link to="/login" style={{ textDecoration: 'none', color: '#007bff' }}>
                        Já tem uma conta? Faça Login
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Cadastro;