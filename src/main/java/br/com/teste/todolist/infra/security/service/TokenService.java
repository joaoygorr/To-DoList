package br.com.teste.todolist.infra.security.service;

import br.com.teste.todolist.modules.auth.User;

public interface TokenService {

    String generateToken(User user);

    String validateToken(String token);
}
