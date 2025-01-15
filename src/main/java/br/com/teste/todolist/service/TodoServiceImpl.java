package br.com.teste.todolist.service;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        return this.todoRepository.save(todo);
    }
}

