package br.com.teste.todolist.service;

import br.com.teste.todolist.module.Todo;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo getTodoById(Long id);

    void deleteTodoById(Long id);
}
