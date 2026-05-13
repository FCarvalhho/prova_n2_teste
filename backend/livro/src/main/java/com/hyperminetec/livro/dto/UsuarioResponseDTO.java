/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import com.hyperminetec.livro.entity.Role;
import com.hyperminetec.livro.entity.Usuario;

/**
 *
 * @author Kayqu
 */
public record UsuarioResponseDTO(
        Long id, 
        String nome, 
        String email, 
        Role role
) {
    
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }
}
