package br.com.teste.todolist.controller;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.record.NewTodoRecord;
import br.com.teste.todolist.record.TodoRecord;
import br.com.teste.todolist.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Todo", description = "Endpoint relacionado a To-DoList")
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo item de To-Do",
            description = "Este endpoint cria um novo item de To-Do, aceitando os dados necessários no corpo da requisição.")
    public ResponseEntity<NewTodoRecord> postTodo(@RequestBody @Valid NewTodoRecord newTodoRecord) {
        Todo response = this.todoService.createTodo(NewTodoRecord.toEntity(newTodoRecord));
        return ResponseEntity.status(HttpStatus.CREATED).body(NewTodoRecord.toDto(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Todo por ID",
            description = "Recupera um Todo pelo seu ID e retorna os dados em formato DTO.")
    public ResponseEntity<TodoRecord> getTodo(@PathVariable Long id) {
        Todo response = this.todoService.getTodoById(id);
        return ResponseEntity.ok(TodoRecord.toDto(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Todo por ID", description = "Deleta o Todo com o ID fornecido.")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        this.todoService.deleteTodoById(id);
        return ResponseEntity.ok("Registro excluído com sucesso");
    }

}
