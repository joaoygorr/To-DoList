package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.enuns.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo getTodoById(Long id);

    void deleteTodoById(Long id);

    Page<Todo> getAllTodos(Status status, LocalDate deadline, Pageable pageable);

    Todo updateTodo(Long id, Todo todo);
}
