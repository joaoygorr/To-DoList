package br.com.teste.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class Exception400 extends RuntimeException {
    public Exception400(String message) {
        super(message);
    }
}