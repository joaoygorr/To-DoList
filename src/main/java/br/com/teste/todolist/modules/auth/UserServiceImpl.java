package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.exceptions.Exception409;
import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.modules.auth.dtos.LoginDTO;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;
import br.com.teste.todolist.record.login.ResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        User user = this.userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new Exception404("Usuário não encontrado"));

        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new LoginDTO(user.getName(), token);
        }
        throw new Exception401("Credenciais inválidas");
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        Optional<User> user = this.userRepository.findByEmail(userDTO.getEmail());
        if (user.isEmpty()) {
            User newUser = new User(userDTO.getName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
            this.userRepository.save(newUser);
            return this.userMapper.toDto(newUser);
        }
        throw new Exception409("Login já cadastrado");
    }
}
