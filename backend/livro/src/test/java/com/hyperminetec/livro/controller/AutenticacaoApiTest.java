package com.hyperminetec.livro.controller;

import com.hyperminetec.livro.dto.LoginRequestDTO;
import com.hyperminetec.livro.dto.UsuarioRequestDTO;
import com.hyperminetec.livro.entity.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutenticacaoApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void deveCadastrarUsuarioERetornarStatus201() {
        UsuarioRequestDTO novoUsuario = new UsuarioRequestDTO("Maria", "maria@email.com", "senha123", Role.CLIENTE);

        given()
            .contentType(ContentType.JSON)
            .body(novoUsuario)
        .when()
            .post("/api/usuarios")
        .then()
            .statusCode(201)
            .body("nome", equalTo("Maria"))
            .body("email", equalTo("maria@email.com"))
            .body("role", equalTo("CLIENTE"))
            .body("id", notNullValue()); 
    }

    @Test
    void deveEfetuarLoginERetornarTokenRoleEIdStatus200() {
         LoginRequestDTO login = new LoginRequestDTO("joao@vendas.com", "cliente123");

        given()
            .contentType(ContentType.JSON)
            .body(login)
        .when()
            .post("/api/login")
        .then()
            .statusCode(200) 
            .body("token", notNullValue())
            .body("role", equalTo("CLIENTE"))
            .body("id", notNullValue()); 
    }
}