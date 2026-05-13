import React, { createContext, useState, useEffect } from 'react';
import { loginAPI } from '../services/authService';

export const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(null);
    const [role, setRole] = useState(null);
    const [userId, setUserId] = useState(null);

    useEffect(() => {
        const storedToken = localStorage.getItem('@VendasLivros:token');
        const storedRole = localStorage.getItem('@VendasLivros:role');
        const storedId = localStorage.getItem('@VendasLivros:id'); 
        
        if (storedToken && storedRole) {
            setToken(storedToken);
            setRole(storedRole);
            setUserId(storedId);
        }
    }, []);

    const signIn = async (email, senha) => {
        try {
            const data = await loginAPI(email, senha);
            
            localStorage.setItem('@VendasLivros:token', data.token);
            localStorage.setItem('@VendasLivros:role', data.role); 
            localStorage.setItem('@VendasLivros:id', data.id); // Salva o ID
            
            setToken(data.token);
            setRole(data.role); 
            setUserId(data.id);
            
            return { success: true };
        } catch (error) {
            return { success: false, message: "E-mail ou senha incorretos." };
        }
    };

    const signOut = () => {
        localStorage.removeItem('@VendasLivros:token');
        localStorage.removeItem('@VendasLivros:role');
        localStorage.removeItem('@VendasLivros:id');
        setToken(null);
        setRole(null);
        setUserId(null);
    };

    return (
        <AuthContext.Provider value={{ signed: !!token, token, role, userId, signIn, signOut }}>
            {children}
        </AuthContext.Provider>
    );
};