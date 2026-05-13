/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.controller;

import com.hyperminetec.livro.dto.LivroRequestDTO;
import com.hyperminetec.livro.dto.LivroResponseDTO;
import com.hyperminetec.livro.service.LivroService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kayqu
 */
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrar(@Valid @RequestBody LivroRequestDTO dto) {
        LivroResponseDTO response = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
}
