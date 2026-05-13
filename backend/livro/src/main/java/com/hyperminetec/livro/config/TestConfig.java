package com.hyperminetec.livro.config;

import com.hyperminetec.livro.entity.Livro;
import com.hyperminetec.livro.entity.Role;
import com.hyperminetec.livro.entity.Usuario;
import com.hyperminetec.livro.repository.LivroRepository;
import com.hyperminetec.livro.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class TestConfig {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, LivroRepository livroRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@vendas.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador do Sistema");
                admin.setEmail("admin@vendas.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                usuarioRepository.save(admin);
            }

            if (usuarioRepository.findByEmail("joao@vendas.com").isEmpty()) {
                Usuario cliente = new Usuario();
                cliente.setNome("João da Silva");
                cliente.setEmail("joao@vendas.com");
                cliente.setSenha(passwordEncoder.encode("cliente123"));
                cliente.setRole(Role.CLIENTE);
                usuarioRepository.save(cliente);
            }
            
            if (livroRepository.count() == 0) {
                Livro livro1 = new Livro(null, "O Senhor dos Anéis", "J.R.R. Tolkien", new BigDecimal("59.90"), "Fantasia", 10);
                Livro livro2 = new Livro(null, "Código Limpo", "Robert C. Martin", new BigDecimal("89.90"), "Tecnologia", 5);
                Livro livro3 = new Livro(null, "1984", "George Orwell", new BigDecimal("35.50"), "Ficção", 20);
                Livro livro4 = new Livro(null, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", new BigDecimal("25.00"), "Infantil", 12);
                livroRepository.saveAll(Arrays.asList(livro1, livro2, livro3, livro4));
            }
        };
    }
}