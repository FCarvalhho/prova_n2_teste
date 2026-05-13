import React, { useContext } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import Cadastro from '../pages/Cadastro';
import Login from '../pages/Login';
import Home from '../pages/Home';

const PrivateRoute = ({ children }) => {
    const { signed } = useContext(AuthContext);
    return signed ? children : <Navigate to="/login" />;
};

const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/cadastro" element={<Cadastro />} />
                
                <Route
                    path="/"
                    element={
                        <PrivateRoute>
                            <Home />
                        </PrivateRoute>
                    }
                />

                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </BrowserRouter>
    );
};

export default AppRoutes;