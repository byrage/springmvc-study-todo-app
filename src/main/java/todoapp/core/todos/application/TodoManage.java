package todoapp.core.todos.application;

import org.springframework.stereotype.Service;
import todoapp.commons.util.StreamUtils;
import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoManage implements TodoFinder, TodoEditor {

    private TodoRepository todoRepository;

    public TodoManage(TodoRepository todoRepository) {

        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAll() {

        return StreamUtils.createStreamFromIterator(todoRepository.findAll().iterator())
                          .collect(Collectors.toList());
    }

    @Override
    public Todo create(String title) {

        Todo todo = Todo.create(title);
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, String title, boolean completed) {

        return todoRepository.findById(id)
                             .map(todo -> todo.update(title, completed))
                             .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public Todo delete(Long id) {

        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(todoRepository::delete);

        return todo.orElseThrow(() -> new TodoNotFoundException(id));
    }
}
