package todoapp.core.todos.infrastructure;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;
import todoapp.core.todos.domain.Todo;
import todoapp.core.todos.domain.TodoRepository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class InMemoryTodoRepository implements TodoRepository, ApplicationRunner {

    private List<Todo> todos = new CopyOnWriteArrayList<>();

    @Override
    public Iterable<Todo> findAll() {

        return Collections.unmodifiableList(todos);
    }

    @Override
    public Iterable<Todo> findByUsername(String username) {

        return null;
    }

    @Override
    public Optional<Todo> findById(Long id) {

        return Optional.empty();
    }

    @Override
    public Todo save(Todo todo) {

        todos.add(todo);
        return todo;
    }

    @Override
    public void delete(Todo todo) {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.todos.add(Todo.create("Task 1!!"));
    }
}
