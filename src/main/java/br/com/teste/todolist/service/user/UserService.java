package br.com.teste.todolist.service.user;

import br.com.teste.todolist.record.login.LoginRecord;
import br.com.teste.todolist.record.login.RegisterRequestRecord;
import br.com.teste.todolist.record.login.ResponseRecord;

public interface UserService {
    ResponseRecord login(LoginRecord loginRecord);

    ResponseRecord register(RegisterRequestRecord requestRecord);
}
