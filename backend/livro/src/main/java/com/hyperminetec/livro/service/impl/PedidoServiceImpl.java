/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service.impl;

import com.hyperminetec.livro.dto.PedidoRequestDTO;
import com.hyperminetec.livro.dto.PedidoResponseDTO;
import com.hyperminetec.livro.entity.Pedido;
import com.hyperminetec.livro.entity.StatusPedido;
import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.exception.RecursoNaoEncontradoException;
import com.hyperminetec.livro.repository.PedidoRepository;
import com.hyperminetec.livro.repository.UsuarioRepository;
import com.hyperminetec.livro.service.PedidoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kayqu
 */
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto) {
        Usuario cliente = usuarioRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado. ID: " + dto.clienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setValorTotal(dto.valorTotal());

        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedidoSalvo);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado."));
        pedido.setStatus(status);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }
}
