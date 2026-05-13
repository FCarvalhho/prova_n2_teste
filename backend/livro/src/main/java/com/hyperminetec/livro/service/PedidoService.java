/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service;

import com.hyperminetec.livro.dto.PedidoRequestDTO;
import com.hyperminetec.livro.dto.PedidoResponseDTO;
import java.util.List;

/**
 *
 * @author Kayqu
 */
public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoRequestDTO dto);

    List<PedidoResponseDTO> listarTodos();

    PedidoResponseDTO atualizarStatus(Long id, com.hyperminetec.livro.entity.StatusPedido status);
}
