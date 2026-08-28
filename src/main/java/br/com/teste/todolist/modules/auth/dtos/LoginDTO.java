package br.com.teste.todolist.modules.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @Email
    @NotBlank(message = "E-mail não pode estar em branco")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String password;
}
