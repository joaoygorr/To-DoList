package br.com.teste.todolist.controller;

import br.com.teste.todolist.record.login.LoginRecord;
import br.com.teste.todolist.record.login.RegisterRequestRecord;
import br.com.teste.todolist.record.login.ResponseRecord;
import br.com.teste.todolist.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoint relacionado autenticação")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário",
            description = "Realiza a autenticação de um usuário com base nas credenciais fornecidas e retorna um token de autenticação.")
    public ResponseEntity<ResponseRecord> login(@RequestBody LoginRecord loginRecord) {
        ResponseRecord user = this.userService.login(loginRecord);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar um novo usuário",
            description = "Cria um novo usuário no sistema com base nos dados fornecidos na requisição. Retorna os detalhes do usuário registrado."
    )
    public ResponseEntity<ResponseRecord> register(@RequestBody RegisterRequestRecord requestRecord) {
        ResponseRecord user = this.userService.register(requestRecord);
        return ResponseEntity.ok(user);
    }
}
