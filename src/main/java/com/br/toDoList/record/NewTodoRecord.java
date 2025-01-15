package com.br.toDoList.record;

import com.br.toDoList.module.Todo;
import com.br.toDoList.module.enuns.Status;
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
}
