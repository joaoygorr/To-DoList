package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.exceptions.Exception401;
import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;
import br.com.teste.todolist.modules.auth.mappers.UserMapper;
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
    public ResponseRecord login(User entity) {
        User user = this.userRepository.findByEmail(entity.getEmail()).orElseThrow(() -> new Exception404("Usuário não encontrado"));

        if (passwordEncoder.matches(entity.getPassword(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new ResponseRecord(user.getName(), token);
        }
        throw new Exception401("Credenciais inválidas");
    }

    @Override
    public UserDTO register(UserDTO dto) {
        Optional<User> user = this.userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) {
            User newUser = new User(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
            this.userRepository.save(newUser);
            return this.userMapper.toDto(newUser);
        }
        throw new Exception401("Credenciais inválidas");
    }
}
