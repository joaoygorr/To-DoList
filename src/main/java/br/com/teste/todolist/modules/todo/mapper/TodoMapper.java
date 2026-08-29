package br.com.teste.todolist.modules.todo.mapper;

import br.com.teste.todolist.exceptions.Exception404;
import br.com.teste.todolist.modules.auth.User;
import br.com.teste.todolist.modules.auth.UserRepository;
import br.com.teste.todolist.modules.todo.Todo;
import br.com.teste.todolist.modules.todo.dtos.TodoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "id", ignore = true)
    public abstract Todo toEntity(TodoDTO todoDto);

    public abstract TodoDTO toDto(Todo todo);

    User mapUser(Long idUser) {
        if (idUser == null) return null;
        return userRepository.findById(idUser).orElseThrow(() -> new Exception404("Usuário não encontrado"));
    }

    Long mapLong(User user) {
        return user != null ? user.getId() : null;
    }
}
