package br.com.teste.todolist.modules.todo;

import br.com.teste.todolist.modules.todo.dtos.TodoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Todo", description = "Endpoint relacionado a To-DoList")
@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "Criar um novo item de To-Do",
            description = "Este endpoint cria um novo item de To-Do, aceitando os dados necessários no corpo da requisição.")
    public ResponseEntity<TodoDTO> postTodo(@RequestBody @Valid TodoDTO todoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.todoService.createTodo(todoDTO));
    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Buscar Todo por ID",
//            description = "Recupera um Todo pelo seu ID e retorna os dados em formato DTO.")
//    public ResponseEntity<TodoRecord> getTodo(@PathVariable Long id) {
//        Todo response = this.todoService.getTodoById(id);
//        return ResponseEntity.ok(TodoRecord.toDto(response));
//    }
//
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Deletar Todo por ID", description = "Deleta o Todo com o ID fornecido.")
//    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
//        this.todoService.deleteTodoById(id);
//        return ResponseEntity.ok("Registro excluído com sucesso");
//    }
//
//    @GetMapping
//    @Operation(summary = "Listar Todos os Todos", description = "Retorna uma lista de todos os registros de Todo.")
//    public ResponseEntity<Page<TodoRecord>> getAllTodos(@RequestParam(required = false) Status status,
//                                                        @RequestParam(required = false) LocalDate deadLine,
//                                                        @RequestParam(required = false) Pageable pageable) {
//        Page<Todo> todosPage = this.todoService.getAllTodos(status, deadLine, pageable);
//        return ResponseEntity.ok(todosPage.map(TodoRecord::toDto));
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Atualiza um registro de Todo",
//            description = "Atualiza os dados de um registro de Todo existente com base no ID fornecido.")
//    public ResponseEntity<NewTodoRecord> updateTodo(@PathVariable Long id, @RequestBody @Valid NewTodoRecord newTodoRecord) {
//        Todo updatedTodo = todoService.updateTodo(id, NewTodoRecord.toEntity(newTodoRecord));
//        return ResponseEntity.ok(NewTodoRecord.toDto(updatedTodo));
//    }
}
