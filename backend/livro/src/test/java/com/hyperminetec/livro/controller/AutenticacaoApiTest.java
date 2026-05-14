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
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutenticacaoApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = 8080;
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

        LoginRequestDTO login
                = new LoginRequestDTO("joao@vendas.com", "cliente123");

        Response resposta = given()
                .contentType(ContentType.JSON)
                .body(login)
                .log().all()
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .body("role", equalTo("CLIENTE"))
                .body("id", notNullValue())
                .extract()
                .response();

        String token = resposta.jsonPath().getString("token");
        String role = resposta.jsonPath().getString("role");
        Integer id = resposta.jsonPath().getInt("id");

        System.out.println("\n======= DADOS DO LOGIN =======");
        System.out.println("TOKEN: " + token);
        System.out.println("ROLE: " + role);
        System.out.println("ID: " + id);
        System.out.println("==============================");
    }
}
