package br.com.teste.todolist.record.login;

import br.com.teste.todolist.module.User;
import jakarta.validation.constraints.NotBlank;

public record LoginRecord(@NotBlank(message = "E-mail não pode estar em branco") String email,
                          @NotBlank(message = "Senha não pode estar em branco") String password) {

    public static User toEntity(LoginRecord user) {
        return new User(
                user.email,
                user.password
        );
    }
}
