package br.com.teste.todolist.record.login;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestRecord(@NotBlank(message = "Nome não pode estar em branco") String name,
                                    @NotBlank(message = "E-mail não pode estar em branco") String email,
                                    @NotBlank(message = "Senha não pode estar em branco") String password) {
}
