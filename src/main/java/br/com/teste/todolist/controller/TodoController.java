package br.com.teste.todolist.controller;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.record.NewTodoRecord;
import br.com.teste.todolist.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Criação de To-DoList", description = "Endpoint relacionado a criação de tarefas")
    public ResponseEntity<NewTodoRecord> postCustomer(@RequestBody @Valid NewTodoRecord newTodoRecord) {
        Todo response = this.todoService.createTodo(NewTodoRecord.toEntity(newTodoRecord));
        return ResponseEntity.status(HttpStatus.CREATED).body(NewTodoRecord.toDto(response));
    }
}
