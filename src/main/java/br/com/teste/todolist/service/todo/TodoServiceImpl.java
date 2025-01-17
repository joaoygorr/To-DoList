package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.infra.security.service.CustomUserDetailsService;
import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.repository.TodoRepository;

import br.com.teste.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, CustomUserDetailsService customUserDetailsService,
                           UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        todo.setUsuario(this.returnUser());
        return this.todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(Long id) {
        Todo todo = this.todoRepository.findById(id)
                .orElseThrow(() -> new Exception404("Item com o id " + id + "não encontrado!"));

        if (!todo.getUsuario().equals(this.returnUser())) {
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
    public Page<Todo> getAllTodos(Pageable pageable) {
        return this.todoRepository.findByUsuarioName(pageable, this.getLoggedUser().getName());
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = this.getTodoById(id);

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setStatus(todo.getStatus());
        existingTodo.setDeadline(todo.getDeadline());
        existingTodo.setUsuario(this.returnUser());

        return this.todoRepository.save(existingTodo);
    }

    private User returnUser() {
        return this.getLoggedUser();
    }

    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                return (User) principal;
            }
            throw new RuntimeException("O principal não é uma instância de UserDetails.");
        }
        throw new RuntimeException("Usuário não autenticado.");
    }
}

