package br.com.teste.todolist.service.user;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.record.login.ResponseRecord;
import br.com.teste.todolist.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, tokenService, passwordEncoder);

        user = new User(1L, "souza", "souza@gmail.com", "123456");
    }

    @Test
    @DisplayName("Usuário cadastrado com sucesso")
    void registerSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("generatedToken");

        userService.register(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("souza", savedUser.getName());
        assertEquals("souza@gmail.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    @DisplayName("Erro ao tentar registrar usuário com credenciais inválidas")
    void registerInvalidCredentialsThrowsException() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(new User(1L, "souza", " ", " ")));

        Exception401 exception = assertThrows(Exception401.class, () -> userService.register(user));
        assertEquals("Credenciais inválidas", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Login feito com sucesso")
    void loginSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("generatedToken");

        ResponseRecord response = userService.login(user);

        assertNotNull(response);
        assertEquals("souza", response.name());
        assertEquals("generatedToken", response.token());

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Login com credenciais inválidas")
    void loginInvalidCredentialsThrowsException() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        Exception401 exception401 = assertThrows(Exception401.class, () -> userService.login(user));

        assertEquals("Credenciais inválidas", exception401.getMessage());

        verify(userRepository).findByEmail(user.getEmail());
        verify(passwordEncoder).matches(user.getPassword(), user.getPassword());
    }
}