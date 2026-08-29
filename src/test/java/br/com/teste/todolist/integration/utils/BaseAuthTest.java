package br.com.teste.todolist.integration.utils;

import br.com.teste.todolist.integration.config.TestConfigs;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;

import static io.restassured.RestAssured.given;

public interface BaseAuthTest {

    default String getToken() {
        return given()
                .baseUri("http://localhost:8080")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(new UserDTO("souza", "souza@gmail.com", "123456"))
                .when().post("/api/auth/register")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}