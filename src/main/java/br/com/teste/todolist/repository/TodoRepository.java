package br.com.teste.todolist.repository;

import br.com.teste.todolist.module.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUsuarioName(Pageable pageable, String name);
}
