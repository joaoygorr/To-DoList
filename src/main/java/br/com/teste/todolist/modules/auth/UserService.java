package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.modules.auth.dtos.LoginDTO;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;

public interface UserService {
    LoginDTO login(LoginDTO loginDTO);

    UserDTO register(UserDTO userDTO);
}
