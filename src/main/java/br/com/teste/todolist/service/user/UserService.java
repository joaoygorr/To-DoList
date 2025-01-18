package br.com.teste.todolist.service.user;

import br.com.teste.todolist.module.User;
import br.com.teste.todolist.record.login.ResponseRecord;

public interface UserService {
    ResponseRecord login(User user);

    ResponseRecord register(User user);
}
