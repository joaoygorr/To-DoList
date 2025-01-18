package br.com.teste.todolist.integration.test;

import br.com.teste.todolist.integration.config.TestConfigs;
import br.com.teste.todolist.integration.containers.AbstractIntegrationTest;
import br.com.teste.todolist.integration.utils.BaseAuthTest;
import br.com.teste.todolist.module.enuns.Status;
import br.com.teste.todolist.record.todo.NewTodoRecord;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TodoIntegrationTest extends AbstractIntegrationTest implements BaseAuthTest {

    private static RequestSpecification specification;

    private static String token = "";

    @BeforeAll
    void setup() {
        token = getToken();
    }

    @Test
    @Order(0)
    @DisplayName("Registro de um tarefa")
    void registerTodo() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/todo")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(new NewTodoRecord("title", "description", Status.PENDENTE, LocalDate.now()))
                .when().post().then()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
        assertTrue(response.contains("title"));
        assertTrue(response.contains("description"));
        assertTrue(response.contains("PENDENTE"));
    }

    @Test
    @Order(1)
    @DisplayName("Buscar tarefa por ID")
    void findTodoById() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/todo")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when().get("/{id}", 1L).then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
        assertTrue(response.contains("title"));
        assertTrue(response.contains("description"));
        assertTrue(response.contains("PENDENTE"));
    }

    @Test
    @Order(2)
    @DisplayName("Buscar todas as tarefas com filtros opcionais")
    void findAllTodosWithFilters() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/todo")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParam("status", Status.PENDENTE.toString())
                .queryParam("deadLine", LocalDate.now().toString())
                .when().get()
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
        assertTrue(response.contains("title"));
        assertTrue(response.contains("description"));
        assertTrue(response.contains("PENDENTE"));
    }

    @Test
    @Order(3)
    @DisplayName("Atualizar uma tarefa existente")
    void updateTodo() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/todo")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        NewTodoRecord updateRequest = new NewTodoRecord(
                "updatedTitle",
                "updatedDescription",
                Status.CONCLUIDO,
                LocalDate.now().plusDays(1)
        );

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(updateRequest)
                .when().put("/{id}", 1L)
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract().body().asString();

        assertNotNull(response);
        assertTrue(response.contains("updatedTitle"));
        assertTrue(response.contains("updatedDescription"));
        assertTrue(response.contains("CONCLUIDO"));
    }

    @Test
    @Order(4)
    @DisplayName("Deletar uma tarefa existente")
    void deleteTodo() {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FRONT)
                .addHeader("Authorization", "Bearer " + token)
                .setBasePath("/api/todo")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        String response = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when().delete("/{id}", 1L)
                .then()
                .statusCode(200)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .extract().body().asString();

        assertNotNull(response);
        assertTrue(response.contains("Registro excluído com sucesso"));
    }
}
