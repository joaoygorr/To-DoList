package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.module.enuns.Status;
import br.com.teste.todolist.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;


    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        todo.setUsuario(this.getLoggedUser());
        return this.todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(Long id) {
        Todo todo = this.todoRepository.findById(id)
                .orElseThrow(() -> new Exception404("Item com o id " + id + " não encontrado!"));

        if (!todo.getUsuario().getId().equals(this.getLoggedUser().getId())) {
            throw new Exception401("Você não tem permissão para visualizar este Todo");
        }

        return todo;
    }

    @Override
    public void deleteTodoById(Long id) {
        getTodoById(id);
        if (this.getTodoById(id) != null) {
            this.todoRepository.deleteById(id);
        }
    }


    @Override
    public Page<Todo> getAllTodos(Status status,LocalDate deadline, Pageable pageable) {
        if (status != null && deadline != null) {
            return todoRepository.findByStatusAndDeadline(status, deadline, pageable);
        } else if (status != null) {
            return todoRepository.findByStatus(status, pageable);
        } else if (deadline != null) {
            return todoRepository.findByDeadline(deadline, pageable);
        }
        return this.todoRepository.findByUsuarioName(pageable, this.getLoggedUser().getName());
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = this.getTodoById(id);

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setStatus(todo.getStatus());
        existingTodo.setDeadline(todo.getDeadline());
        existingTodo.setUsuario(this.getLoggedUser());

        return this.todoRepository.save(existingTodo);
    }

    protected User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(principal -> (User) principal)
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado ou principal inválido."));
    }
}

