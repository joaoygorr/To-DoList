package com.br.toDoList.module;

import com.br.toDoList.module.enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Date creationDate;

    @Column(name = "deadline")
    private Date deadline;

    public Todo(String title, String description, Status status, Date deadline, Date creationDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

}
