package br.com.teste.todolist.modules.auth;

import br.com.teste.todolist.infra.security.service.TokenService;
import br.com.teste.todolist.modules.auth.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private TokenService tokenService;

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "token", source = "entity", qualifiedByName = "mapToken")
    public abstract UserDTO toDto(User entity);

    @Named("mapToken")
    String mapToken(User user) {
        return this.tokenService.generateToken(user);
    }
}
