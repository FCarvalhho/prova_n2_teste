/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.dto;

import com.hyperminetec.livro.entity.Pedido;
import com.hyperminetec.livro.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Kayqu
 */
public record PedidoResponseDTO(
        Long id, 
        Long clienteId, 
        String nomeCliente, 
        LocalDateTime dataPedido, 
        BigDecimal valorTotal, 
        StatusPedido status
) {
    public PedidoResponseDTO(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente().getId(), pedido.getCliente().getNome(), 
             pedido.getDataPedido(), pedido.getValorTotal(), pedido.getStatus());
    }
}
