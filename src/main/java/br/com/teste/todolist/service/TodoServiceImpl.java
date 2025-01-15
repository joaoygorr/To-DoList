package br.com.teste.todolist.service;

import br.com.teste.todolist.exceptions.Exception404;
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

    @Override
    public Todo getTodoById(Long id) {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new Exception404("Item com o id " + id + "não encontrado!"));
    }

    @Override
    public void deleteTodoById(Long id) {
        if (this.getTodoById(id) != null) {
            this.todoRepository.deleteById(id);
        }
    }
}

