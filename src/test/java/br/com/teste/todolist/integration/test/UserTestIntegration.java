package br.com.teste.todolist.integration.test;

import br.com.teste.todolist.integration.config.TestConfigs;
import br.com.teste.todolist.integration.containers.AbstractIntegrationTest;
import br.com.teste.todolist.record.login.LoginRecord;
import br.com.teste.todolist.record.login.RegisterRequestRecord;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserTestIntegration extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    @Test
    @Order(0)
    @DisplayName("Registro de um usuário")
    void registerUser() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .setBasePath("/api/auth/register")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(new RegisterRequestRecord("souza", "souza@gmail.com", "123456"))
                .when().post().then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
    }

    @Test
    @Order(1)
    @DisplayName("Login de um usuário")
    void loginUser() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .setBasePath("/api/auth/login")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(new LoginRecord("souza@gmail.com", "123456"))
                .when().post().then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
    }

}
