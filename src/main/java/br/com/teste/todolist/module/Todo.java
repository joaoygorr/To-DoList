package br.com.teste.todolist.module;

import br.com.teste.todolist.module.enuns.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "todos",
        indexes = @Index(name = "idx_todos_usuario_name", columnList = "usuario_name"))
public class Todo {

    @Id
    @Column(name = "id_todo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_usuario_todo"))
    private User usuario;

    public Todo(String title, String description, Status status, LocalDate deadline, LocalDate creationDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

    public Todo(Long id, String title, String description, Status status, LocalDate deadline, LocalDate creationDate,
                User usuario) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
        this.usuario = usuario;
    }

    public Todo(Long id, String title, String description, Status status, LocalDate deadline, LocalDate creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

    public Todo() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

}
