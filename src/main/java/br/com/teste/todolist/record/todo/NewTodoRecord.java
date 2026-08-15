package br.com.teste.todolist.record.todo;

import br.com.teste.todolist.modules.todo.Todo;
import br.com.teste.todolist.modules.todo.enuns.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewTodoRecord(@NotBlank(message = "Título não pode estar em branco") String title,
                            String description,
                            @NotNull(message = "O status não pode ser nulo") Status status,
                            @NotNull(message = "O prazo não pode ser nulo") LocalDate deadLine) {

    public static Todo toEntity(NewTodoRecord newTodoRecord) {
        return new Todo(
                newTodoRecord.title,
                newTodoRecord.description,
                newTodoRecord.status,
                newTodoRecord.deadLine
        );
    }

    public static NewTodoRecord toDto(Todo todo) {
        return new NewTodoRecord(
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getDeadLine()
        );
    }
}

