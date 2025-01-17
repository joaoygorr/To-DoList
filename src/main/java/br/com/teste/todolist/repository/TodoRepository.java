package br.com.teste.todolist.repository;

import br.com.teste.todolist.module.Todo;
import br.com.teste.todolist.module.enuns.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUsuarioName(Pageable pageable, String name);

    Page<Todo> findByStatus(Status status, Pageable pageable);

    Page<Todo> findByDeadline(LocalDate deadline, Pageable pageable);

    Page<Todo> findByStatusAndDeadline(Status status, LocalDate deadline, Pageable pageable);
}
