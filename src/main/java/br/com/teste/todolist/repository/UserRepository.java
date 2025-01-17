package br.com.teste.todolist.repository;

import br.com.teste.todolist.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optiona<User> findByEmail(String email);
}
