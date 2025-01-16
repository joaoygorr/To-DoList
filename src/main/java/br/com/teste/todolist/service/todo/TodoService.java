package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.module.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo getTodoById(Long id);

    void deleteTodoById(Long id);

    Page<Todo> getAllTodos(Pageable pageable);

    Todo updateTodo(Long id, Todo todo);
}
