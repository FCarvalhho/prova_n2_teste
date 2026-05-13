/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service;

import com.hyperminetec.livro.dto.UsuarioRequestDTO;
import com.hyperminetec.livro.dto.UsuarioResponseDTO;
import java.util.List;

/**
 *
 * @author Kayqu
 */
public interface UsuarioService {
    UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto);
    UsuarioResponseDTO buscarPorId(Long id);
    List<UsuarioResponseDTO> listarTodos();
}
