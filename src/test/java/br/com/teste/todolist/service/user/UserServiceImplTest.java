package br.com.teste.todolist.service.user;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.record.login.LoginRecord;
import br.com.teste.todolist.record.login.RegisterRequestRecord;
import br.com.teste.todolist.record.login.ResponseRecord;
import br.com.teste.todolist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private RegisterRequestRecord defaultRequest;

    private LoginRecord loginRecord;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, tokenService, passwordEncoder);

        defaultRequest = new RegisterRequestRecord("souza", "souza@gmail.com", "123456");
        loginRecord = new LoginRecord("souza@gmail.com", "123456");
        user = new User(1L, "souza", "souza@gmail.com", "123456");
    }

    @Test
    @DisplayName("Usuário cadastrado com sucesso")
    void registerSuccess() {
        when(userRepository.findByEmail(defaultRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(defaultRequest.password())).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("generatedToken");

        ResponseRecord response = userService.register(defaultRequest);

        assertNotNull(response);
        assertEquals("souza", response.name());
        assertEquals("generatedToken", response.token());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Erro ao tentar registrar usuário com credenciais inválidas")
    void registerInvalidCredentialsThrowsException() {
        when(userRepository.findByEmail(defaultRequest.email()))
                .thenReturn(Optional.of(new User(1L, "souza", " ", " ")));

        Exception401 exception = assertThrows(Exception401.class, () -> userService.register(defaultRequest));
        assertEquals("Credenciais inválidas", exception.getMessage());

        verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    @DisplayName("Login feito com sucesso")
    void login() {
        when(userRepository.findByEmail(loginRecord.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRecord.password(), user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("generatedToken");

        ResponseRecord response = userService.login(loginRecord);

        assertNotNull(response);
        assertEquals("souza", response.name());
        assertEquals("generatedToken", response.token());

        verify(userRepository).findByEmail(loginRecord.email());
    }

    @Test
    @DisplayName("Login com credenciais inválidas")
    void loginInvalidCredentialsThrowsException() {
        when(userRepository.findByEmail(loginRecord.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRecord.password(), user.getPassword())).thenReturn(false);

        Exception401 exception401 = assertThrows(Exception401.class, () -> userService.login(loginRecord));

        assertEquals("Credenciais inválidas", exception401.getMessage());

        verify(userRepository).findByEmail(loginRecord.email());
        verify(passwordEncoder).matches(loginRecord.password(), user.getPassword());
    }
}