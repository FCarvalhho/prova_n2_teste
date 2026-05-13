import React, { useState, useContext } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import styles from './Login.module.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');

    const { signIn } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setErro('');

        if (!email || !senha) {
            setErro('Por favor, preencha todos os campos.');
            return;
        }

        const response = await signIn(email, senha);

        if (response.success) {
            navigate('/');
        } else {
            setErro(response.message);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.formBox}>
                <h2>Vendas de Livros</h2>
                {erro && <div className={styles.error}>{erro}</div>}

                <form onSubmit={handleLogin}>
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
                            placeholder="Digite sua senha"
                        />
                    </div>
                    <button type="submit" className={styles.button}>Entrar</button>
                </form>
                <div style={{ textAlign: 'center', marginTop: '1rem' }}>
                    <Link to="/cadastro" style={{ textDecoration: 'none', color: '#007bff' }}>
                        Não tem conta? Cadastre-se
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Login;