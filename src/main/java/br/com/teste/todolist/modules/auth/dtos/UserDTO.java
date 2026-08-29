package br.com.teste.todolist.modules.auth.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotBlank(message = "Nome não pode estar em branco")
    private String name;

    @Email
    @NotBlank(message = "E-mail não pode estar em branco")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String password;

    private String token;

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
