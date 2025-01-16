package br.com.teste.todolist.service.user;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.module.User;
import br.com.teste.todolist.record.login.LoginRecord;
import br.com.teste.todolist.record.login.RegisterRequestRecord;
import br.com.teste.todolist.record.login.ResponseRecord;
import br.com.teste.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseRecord login(LoginRecord loginRecord) {
        User user = this.userRepository.findByEmail(loginRecord.email())
                .orElseThrow(() -> new Exception404("Usuário não encontrado"));

        if (passwordEncoder.matches(loginRecord.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new ResponseRecord(user.getName(), token);
        }
        throw new Exception401("Credenciais inválidas");
    }

    @Override
    public ResponseRecord register(RegisterRequestRecord requestRecord) {
        Optional<User> user = this.userRepository.findByEmail(requestRecord.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(requestRecord.password()));
            newUser.setEmail(requestRecord.email());
            newUser.setName(requestRecord.name());
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return new ResponseRecord(newUser.getName(), token);
        }

        throw new Exception401("Credenciais inválidas");
    }
}
