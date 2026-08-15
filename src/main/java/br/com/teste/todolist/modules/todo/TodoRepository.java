package br.com.teste.todolist.modules.todo;

import br.com.teste.todolist.modules.todo.enuns.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUsuarioName(Pageable pageable, String name);

    Page<Todo> findByStatus(Status status, Pageable pageable);

    Page<Todo> findByDeadLine(LocalDate deadLine, Pageable pageable);

    Page<Todo> findByStatusAndDeadLine(Status status, LocalDate deadLine, Pageable pageable);

    Page<Todo> findByUsuarioIdAndStatusAndDeadLine(Long User, Status status, LocalDate deadline, Pageable pageable);
}
