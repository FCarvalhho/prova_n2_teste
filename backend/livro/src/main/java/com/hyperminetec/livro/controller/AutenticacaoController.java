/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hyperminetec.livro.controller;

import com.hyperminetec.livro.dto.LoginRequestDTO;
import com.hyperminetec.livro.dto.LoginResponseDTO;
import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.repository.UsuarioRepository;
import com.hyperminetec.livro.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Kayqu
 */
@RestController
@RequestMapping("/api/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> efetuarLogin(@RequestBody @Valid LoginRequestDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        manager.authenticate(authenticationToken);
        Usuario usuario = usuarioRepository.findByEmail(dto.email()).orElseThrow();
        var tokenJWT = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(tokenJWT, usuario.getRole().name(), usuario.getId()));
    }
}
