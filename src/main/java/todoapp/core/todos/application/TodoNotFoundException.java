package todoapp.core.todos.application;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(long id) {

        super(String.format("id를 찾을 수 없습니다. id:%d", id));
    }
}
