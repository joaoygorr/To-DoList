package br.com.teste.todolist.modules.todo;

import br.com.teste.todolist.modules.todo.dtos.TodoDTO;
import br.com.teste.todolist.modules.todo.enuns.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TodoService {

    TodoDTO createTodo(TodoDTO todoDTO);

    Todo getTodoById(Long id);

    void deleteTodoById(Long id);

    Page<Todo> getAllTodos(Status status, LocalDate deadLine, Pageable pageable);

    Todo updateTodo(Long id, Todo todo);
}
