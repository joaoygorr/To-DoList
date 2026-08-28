package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.modules.auth.dtos.LoginDTO;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoint relacionado autenticação")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário",
            description = "Realiza a autenticação de um usuário com base nas credenciais fornecidas e retorna um token de autenticação.")
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginDTO LoginDTO) {
        return ResponseEntity.ok(this.userService.login(LoginDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar um novo usuário",
            description = "Cria um novo usuário no sistema com base nos dados fornecidos na requisição. Retorna os detalhes do usuário registrado.")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.register(userDTO));
    }
}
