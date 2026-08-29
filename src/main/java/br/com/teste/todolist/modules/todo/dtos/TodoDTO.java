package br.com.teste.todolist.modules.todo.dtos;

import br.com.teste.todolist.modules.todo.enuns.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;

    @NotBlank(message = "Título obrigatório")
    private String title;

    private String description;

    @NotNull(message = "Status é obrigatório")
    private Status status;

    private LocalDate creationDate;

    @NotNull(message = "DeadLine é obrigatório")
    private LocalDate deadLine;

    private Long usuario;
}
