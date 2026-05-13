/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 *
 * @author Kayqu
 */
public record PedidoRequestDTO(
        @NotNull(message = "O ID do cliente é obrigatório") 
        Long clienteId,

        @NotNull(message = "O valor total é obrigatório") 
        @Positive(message = "O valor total deve ser maior que zero") 
        BigDecimal valorTotal
) {}
