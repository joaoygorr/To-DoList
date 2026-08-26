package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.modules.auth.dtos.UserDTO;
import br.com.teste.todolist.record.login.ResponseRecord;

public interface UserService {
    ResponseRecord login(User user);

    UserDTO register(UserDTO userDTO);
}
