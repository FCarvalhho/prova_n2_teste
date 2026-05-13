/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import com.hyperminetec.livro.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Kayqu
 */
public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório") 
        String nome,

        @NotBlank(message = "O email é obrigatório") 
        @Email(message = "Formato de email inválido") 
        String email,

        @NotBlank(message = "A senha é obrigatória") 
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") 
        String senha,

        @NotNull(message = "A role (perfil) é obrigatória") 
        Role role
) {}
