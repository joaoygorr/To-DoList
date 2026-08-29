package br.com.teste.todolist.modules.todo;

import br.com.teste.todolist.modules.auth.User;
import br.com.teste.todolist.modules.todo.enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(name = "idx_todos_usuario_name", columnList = "usuario_id"))
@EqualsAndHashCode(of = "id")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_usuario_todo"), nullable = false)
    private User usuario;

    public Todo(String title, String description, Status status, LocalDate deadLine) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadLine = deadLine;
    }

    public Todo(Long id, String title, String description, Status status, LocalDate deadLine, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadLine = deadLine;
    }
}
