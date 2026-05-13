package com.hyperminetec.livro;

import com.hyperminetec.livro.dto.UsuarioRequestDTO;
import com.hyperminetec.livro.dto.UsuarioResponseDTO;
import com.hyperminetec.livro.entity.Role;
import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.exception.RegraNegocioException;
import com.hyperminetec.livro.repository.UsuarioRepository;
import com.hyperminetec.livro.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    private UsuarioRequestDTO requestDTO;
    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        requestDTO = new UsuarioRequestDTO("João", "joao@email.com", "senha123", Role.CLIENTE);
        usuarioMock = new Usuario(1L, "João", "joao@email.com", "senhaCriptografada", Role.CLIENTE);
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        Mockito.when(repository.findByEmail(requestDTO.email())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(requestDTO.senha())).thenReturn("senhaCriptografada");
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuarioMock);

        UsuarioResponseDTO response = service.cadastrar(requestDTO);
        
        Assertions.assertNotNull(response);
        Assertions.assertEquals("joao@email.com", response.email());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Usuario.class));
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailDuplicado() {
        Mockito.when(repository.findByEmail(requestDTO.email())).thenReturn(Optional.of(usuarioMock));

        RegraNegocioException exception = Assertions.assertThrows(RegraNegocioException.class, () -> {
            service.cadastrar(requestDTO);
        });

        Assertions.assertEquals("Já existe um usuário cadastrado com este email.", exception.getMessage());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Usuario.class));
    }
}
