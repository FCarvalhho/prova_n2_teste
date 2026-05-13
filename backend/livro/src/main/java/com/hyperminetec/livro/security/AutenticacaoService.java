/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.security;

import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.repository.UsuarioRepository;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kayqu
 */
@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))
        );
    }
}
