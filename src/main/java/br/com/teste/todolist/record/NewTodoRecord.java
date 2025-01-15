package br.com.teste.todolist.record;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.enuns.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record NewTodoRecord(@NotBlank(message = "Título não pode estar em branco") String title,
                            String description,
                            @NotNull(message = "O status não pode ser nulo") Status status,
                            @NotNull(message = "O prazo não pode ser nulo") Date deadline) {

    public static Todo toEntity(NewTodoRecord newTodoRecord) {
        return new Todo(
                newTodoRecord.title,
                newTodoRecord.description,
                newTodoRecord.status,
                new Date(),
                newTodoRecord.deadline
        );
    }

    public static NewTodoRecord toDto(Todo todo) {
        return new NewTodoRecord(
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getDeadline()
        );
    }
}

