package br.com.teste.todolist.service.todo;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.module.enuns.Status;
import br.com.teste.todolist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private User user;

    private Todo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todoService = new TodoServiceImpl(todoRepository);

        user = new User(1L, "souza", "souza@gmail.com", "123456");
        todo = new Todo(1L, "title", "description", Status.PENDENTE,
                LocalDate.now(), LocalDate.now().plusDays(7), user);

        Authentication authentication = mock(Authentication.class);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Todo criado com sucesso")
    void createTodoSuccess() {
        todoService.createTodo(todo);

        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(todoRepository).save(todoCaptor.capture());

        Todo savedTodo = todoCaptor.getValue();

        assertNotNull(savedTodo);
        assertEquals("title", savedTodo.getTitle());
        assertEquals("description", savedTodo.getDescription());
        assertEquals(Status.PENDENTE, savedTodo.getStatus());
        assertEquals(user, savedTodo.getUsuario());
    }

    @Test
    @DisplayName("Retorna todo com sucesso")
    void getTodoByIdSuccess() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo foundTodo = todoService.getTodoById(1L);

        assertNotNull(foundTodo);
        assertEquals(todo.getId(), foundTodo.getId());
        assertEquals(todo.getTitle(), foundTodo.getTitle());
        assertEquals(todo.getDescription(), foundTodo.getDescription());
        assertEquals(todo.getUsuario(), foundTodo.getUsuario());

        verify(todoRepository).findById(1L);
    }

    @Test
    @DisplayName("Erro ao buscar um Todo inexistente")
    void getTodoByIdNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception404 exception = assertThrows(Exception404.class, () -> todoService.getTodoById(1L));

        assertEquals("Item com o id 1 não encontrado!", exception.getMessage());
        verify(todoRepository).findById(1L);
    }

    @Test
    @DisplayName("Erro de permissão ao buscar um Todo de outro usuário")
    void getTodoByIdUnauthorized() {
        User otherUser = new User(2L, "João", "joao@gmail.com", "password456");
        Todo otherTodo = new Todo(2L, "Tarefa de outro usuário", "Descrição", Status.PENDENTE,
                LocalDate.now(), LocalDate.now().plusDays(2), otherUser);

        when(todoRepository.findById(2L)).thenReturn(Optional.of(otherTodo));

        Exception401 exception = assertThrows(Exception401.class, () -> todoService.getTodoById(2L));

        assertEquals("Você não tem permissão para visualizar este Todo", exception.getMessage());
        verify(todoRepository).findById(2L);
    }

    @Test
    @DisplayName("Todo excluido com sucesso")
    void deleteTodoByIdSuccess() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.deleteTodoById(1L);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(todoRepository).deleteById(idCaptor.capture());
        assertEquals(1L, idCaptor.getValue());

        verify(todoRepository, times(2)).findById(1L);
    }

    @Test
    @DisplayName("Lançar exceção ao tentar deletar um Todo inexistente")
    void deleteTodoByIdNotFoundThrowsException() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception404 exception = assertThrows(Exception404.class, () -> todoService.deleteTodoById(1L));
        assertEquals("Item com o id 1 não encontrado!", exception.getMessage());

        verify(todoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Lançar exceção ao tentar deletar Todo de outro usuário")
    void deleteTodoByIdUnauthorizedThrowsException() {
        User anotherUser = new User(2L, "joao", "joao@gmail.com", "password123");
        Todo anotherTodo = new Todo(2L, "Estudar Java", "Revisar anotações de Java", Status.PENDENTE,
                LocalDate.now(), LocalDate.now().plusDays(3), anotherUser);

        when(todoRepository.findById(2L)).thenReturn(Optional.of(anotherTodo));

        Exception401 exception = assertThrows(Exception401.class, () -> todoService.deleteTodoById(2L));
        assertEquals("Você não tem permissão para visualizar este Todo", exception.getMessage());

        verify(todoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Busca todo com sucesso")
    void getAllTodosSuccess() {
        Status status = Status.PENDENTE;
        LocalDate deadline = LocalDate.of(2023, 12, 31);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Todo> expectedPage = new PageImpl<>(List.of(new Todo(), new Todo()));

        when(todoRepository.findByStatusAndDeadline(status, deadline, pageable)).thenReturn(expectedPage);

        Page<Todo> result = todoService.getAllTodos(status, deadline, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(todoRepository).findByStatusAndDeadline(status, deadline, pageable);
    }

    @Test
    @DisplayName("Obter Todos os Todos com Status")
    void getAllTodosWithStatus() {
        Status status = Status.PENDENTE;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Todo> expectedPage = new PageImpl<>(List.of(new Todo(), new Todo()));

        when(todoRepository.findByStatus(status, pageable)).thenReturn(expectedPage);

        Page<Todo> result = todoService.getAllTodos(status, null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(todoRepository).findByStatus(status, pageable);
    }

    @Test
    @DisplayName("Obter Todos os Todos com Deadline")
    void getAllTodosWithDeadline() {
        LocalDate deadline = LocalDate.of(2023, 12, 31);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Todo> expectedPage = new PageImpl<>(List.of(new Todo(), new Todo()));

        when(todoRepository.findByDeadline(deadline, pageable)).thenReturn(expectedPage);

        Page<Todo> result = todoService.getAllTodos(null, deadline, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(todoRepository).findByDeadline(deadline, pageable);
    }

    @Test
    @DisplayName("Obter Todos os Todos do Usuário")
    void getAllTodosForUser() {
        Pageable pageable = PageRequest.of(0, 10);
        String loggedUserName = "souza";

        Page<Todo> expectedPage = new PageImpl<>(List.of(new Todo(), new Todo()));

        when(todoRepository.findByUsuarioName(pageable, loggedUserName)).thenReturn(expectedPage);
        when(todoService.getLoggedUser()).thenReturn(new User(1L, "souza",
                "souza@gmail.com", "password"));

        Page<Todo> result = todoService.getAllTodos(null, null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(todoRepository).findByUsuarioName(pageable, loggedUserName);
    }

    @Test
    @DisplayName("Atualiza todo com sucesso")
    void updateTodoSuccess() {
        Todo updatedTodo = new Todo("Estudar Spring", "Estudar anotações do Spring", Status.CONCLUIDO,
                LocalDate.of(2025, 12, 25));

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo result = todoService.updateTodo(1L, updatedTodo);

        assertNotNull(result);
        assertEquals("Estudar Spring", result.getTitle());
        assertEquals("Estudar anotações do Spring", result.getDescription());
        assertEquals(Status.CONCLUIDO, result.getStatus());
        assertEquals(LocalDate.of(2025, 12, 25), result.getDeadline());
        assertEquals(user, result.getUsuario());

        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(todoRepository).save(todoCaptor.capture());

        Todo savedTodo = todoCaptor.getValue();
        assertEquals("Estudar Spring", savedTodo.getTitle());
        assertEquals("Estudar anotações do Spring", savedTodo.getDescription());
        assertEquals(Status.CONCLUIDO, savedTodo.getStatus());
        assertEquals(LocalDate.of(2025, 12, 25), savedTodo.getDeadline());
    }

    @Test
    @DisplayName("Tentar atualizar um Todo inexistente")
    void updateTodoNotFoundThrowsException() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception404 exception = assertThrows(Exception404.class, () -> todoService.updateTodo(1L, new Todo()));
        assertEquals("Item com o id 1 não encontrado!", exception.getMessage());

        verify(todoRepository, never()).save(any(Todo.class));
    }
}