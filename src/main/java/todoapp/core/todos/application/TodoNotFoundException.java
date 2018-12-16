package todoapp.core.todos.application;

import todoapp.commons.SystemException;

public class TodoNotFoundException extends SystemException {

    private long id;

    public TodoNotFoundException(long id) {

        super(String.format("id를 찾을 수 없습니다. id:%d", id));
        this.id = id;
    }

    @Override
    public Object[] getArguments() {

        return new Object[] {String.valueOf(id)};
    }
}
