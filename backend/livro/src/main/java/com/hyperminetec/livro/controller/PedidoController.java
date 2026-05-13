/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.controller;

import com.hyperminetec.livro.dto.PedidoRequestDTO;
import com.hyperminetec.livro.dto.PedidoResponseDTO;
import com.hyperminetec.livro.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kayqu
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = service.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id, 
            @RequestParam com.hyperminetec.livro.entity.StatusPedido status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }
}
