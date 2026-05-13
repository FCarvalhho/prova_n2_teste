/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service.impl;

import com.hyperminetec.livro.dto.LivroRequestDTO;
import com.hyperminetec.livro.dto.LivroResponseDTO;
import com.hyperminetec.livro.entity.Livro;
import com.hyperminetec.livro.exception.RecursoNaoEncontradoException;
import com.hyperminetec.livro.repository.LivroRepository;
import com.hyperminetec.livro.service.LivroService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kayqu
 */
@Service
public class LivroServiceImpl implements LivroService {

    private final LivroRepository repository;

    public LivroServiceImpl(LivroRepository repository) {
        this.repository = repository;
    }

    @Override
    public LivroResponseDTO cadastrar(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setPreco(dto.preco());
        livro.setCategoria(dto.categoria());
        livro.setEstoque(dto.estoque());

        Livro livroSalvo = repository.save(livro);
        return new LivroResponseDTO(livroSalvo);
    }

    @Override
    public LivroResponseDTO buscarPorId(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado. ID: " + id));
        return new LivroResponseDTO(livro);
    }

    @Override
    public List<LivroResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(LivroResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado para exclusão. ID: " + id));
        repository.delete(livro);
    }
    
    @Override
    public LivroResponseDTO atualizar(Long id, LivroRequestDTO dto) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado. ID: " + id));

        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setPreco(dto.preco());
        livro.setCategoria(dto.categoria());
        livro.setEstoque(dto.estoque());

        Livro livroAtualizado = repository.save(livro);
        return new LivroResponseDTO(livroAtualizado);
    }
}
