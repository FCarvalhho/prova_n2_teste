/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 *
 * @author Kayqu
 */
public record LivroRequestDTO(
        @NotBlank(message = "O título é obrigatório") 
        String titulo,

        @NotBlank(message = "O autor é obrigatório") 
        String autor,

        @NotNull(message = "O preço é obrigatório") 
        @Positive(message = "O preço deve ser maior que zero") 
        BigDecimal preco,

        @NotBlank(message = "A categoria é obrigatória") 
        String categoria,

        @NotNull(message = "O estoque é obrigatório") 
        @PositiveOrZero(message = "O estoque não pode ser negativo") 
        Integer estoque
) {}