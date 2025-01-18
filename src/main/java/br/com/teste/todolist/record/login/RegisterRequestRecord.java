package br.com.teste.todolist.record.login;

import br.com.teste.todolist.module.User;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestRecord(@NotBlank(message = "Nome não pode estar em branco") String name,
                                    @NotBlank(message = "E-mail não pode estar em branco") String email,
                                    @NotBlank(message = "Senha não pode estar em branco") String password) {

    public static User toEntity(RegisterRequestRecord requestRecord) {
        return new User(
                requestRecord.name,
                requestRecord.email(),
                requestRecord.password()
        );
    }
}
