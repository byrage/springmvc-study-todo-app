package todoapp.core.todos.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
// TODO : exception 처리가 도메인에 있어서 좋은 로직은 아님
public class TodoCreationException extends RuntimeException {

    public TodoCreationException(String message) {
        super(message);
    }
}
