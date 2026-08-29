package br.com.teste.todolist.modules.todo;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.modules.auth.User;
import br.com.teste.todolist.modules.todo.dtos.TodoDTO;
import br.com.teste.todolist.modules.todo.enuns.Status;
import br.com.teste.todolist.modules.todo.mapper.TodoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Transactional
    @Override
    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = todoMapper.toEntity(todoDTO);
        todo.setUsuario(getLoggedUser());
        return todoMapper.toDto(todoRepository.save(todo));
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

    @Transactional
    @Override
    public void deleteTodoById(Long id) {
        Todo todo = getTodoById(id);
        this.todoRepository.delete(todo);
    }

    @Override
    public Page<Todo> getAllTodos(Status status,LocalDate deadLine, Pageable pageable) {
        if (status != null && deadLine != null) {
            return todoRepository.findByUsuarioIdAndStatusAndDeadLine(getLoggedUser().getId(), status, deadLine, pageable);
        } else if (status != null) {
            return todoRepository.findByStatus(status, pageable);
        } else if (deadLine != null) {
            return todoRepository.findByDeadLine(deadLine, pageable);
        }
        return this.todoRepository.findByUsuarioName(pageable, this.getLoggedUser().getName());
    }

    @Transactional
    @Override
    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = this.getTodoById(id);

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setStatus(todo.getStatus());
        existingTodo.setDeadLine(todo.getDeadLine());
        existingTodo.setUsuario(this.getLoggedUser());

        return this.todoRepository.save(existingTodo);
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(principal -> (User) principal)
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado ou principal inválido."));
    }
}

