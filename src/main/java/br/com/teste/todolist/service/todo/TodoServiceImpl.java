package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    @Override
    public Page<Todo> getAllTodos(Pageable pageable) {
        return this.todoRepository.findAll(pageable);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new Exception404("Todo com ID " + id + " não encontrado."));

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setCreationDate(LocalDate.now());
        existingTodo.setStatus(todo.getStatus());
        existingTodo.setDeadline(todo.getDeadline());

        return todoRepository.save(existingTodo);
    }
}

