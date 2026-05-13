/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import com.hyperminetec.livro.entity.Livro;
import java.math.BigDecimal;

/**
 *
 * @author Kayqu
 */
public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        BigDecimal preco,
        String categoria,
        Integer estoque
        ) {

    public LivroResponseDTO(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getPreco(), livro.getCategoria(), livro.getEstoque());
    }

}
