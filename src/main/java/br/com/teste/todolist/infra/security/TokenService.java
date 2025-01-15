package br.com.teste.todolist.infra.security;

import br.com.teste.todolist.module.User;

public interface TokenService {

    String generateToken(User user);

    String validateToken(String token);
}
