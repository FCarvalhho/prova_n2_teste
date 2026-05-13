/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service;

import com.hyperminetec.livro.dto.LivroRequestDTO;
import com.hyperminetec.livro.dto.LivroResponseDTO;
import java.util.List;

/**
 *
 * @author Kayqu
 */
public interface LivroService {
    LivroResponseDTO cadastrar(LivroRequestDTO dto);
    LivroResponseDTO buscarPorId(Long id);
    List<LivroResponseDTO> listarTodos();
    void deletar(Long id);
    LivroResponseDTO atualizar(Long id, LivroRequestDTO dto);
}
