package br.com.teste.todolist.module;

import br.com.teste.todolist.module.enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_usuario_todo"))
    private User usuario;

    public Todo(String title, String description, Status status, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
    }

    public Todo(Long id, String title, String description, Status status, LocalDate deadline, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }
}
