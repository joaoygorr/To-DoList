package br.com.teste.todolist.record;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.enuns.Status;

import java.time.LocalDate;

public record TodoRecord(Long id,
                         String title,
                         String description,
                         Status status,
                         LocalDate creationDate,
                         LocalDate deadline) {

    public static TodoRecord toDto(Todo todo) {
        return new TodoRecord(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getCreationDate(),
                todo.getDeadline()
        );
    }

    public static Todo toEntity(TodoRecord todoRecord) {
        return new Todo(
                todoRecord.id,
                todoRecord.title(),
                todoRecord.description(),
                todoRecord.status,
                todoRecord.creationDate,
                todoRecord.deadline
        );
    }
}
