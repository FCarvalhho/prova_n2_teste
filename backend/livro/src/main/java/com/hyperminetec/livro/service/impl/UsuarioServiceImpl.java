/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.service.impl;

import com.hyperminetec.livro.dto.UsuarioRequestDTO;
import com.hyperminetec.livro.dto.UsuarioResponseDTO;
import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.exception.RecursoNaoEncontradoException;
import com.hyperminetec.livro.exception.RegraNegocioException;
import com.hyperminetec.livro.repository.UsuarioRepository;
import com.hyperminetec.livro.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Kayqu
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        usuario.setRole(dto.role());

        Usuario usuarioSalvo = repository.save(usuario);
        return new UsuarioResponseDTO(usuarioSalvo);
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado. ID: " + id));
        return new UsuarioResponseDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }
}
