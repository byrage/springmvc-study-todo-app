package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todos.application.*;
import todoapp.core.todos.domain.Todo;
import todoapp.web.model.FeatureTogglesProperties;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
public class TodoManageRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private TodoFinder todoFinder;
    private TodoEditor todoEditor;
    private FeatureTogglesProperties featureTogglesProperties;

    public TodoManageRestController(TodoFinder todoFinder, TodoEditor todoEditor, FeatureTogglesProperties featureTogglesProperties) {

        this.todoFinder = todoFinder;
        this.todoEditor = todoEditor;
        this.featureTogglesProperties = featureTogglesProperties;
    }

    @GetMapping("/api/todos")
    public List<Todo> todos() {

        return todoFinder.getAll();
    }

    @PostMapping("/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid WriteTodoCommand command) {

        log.debug("request body:{}", command);
        todoEditor.create(command.getTitle());

    }

    @PutMapping("/api/todos/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid WriteTodoCommand command){

        log.debug("update: pathVariable:{}, requestBody:{}", id, command);
        todoEditor.update(id, command.getTitle(), command.getCompleted());
    }

    @DeleteMapping("/api/todos/{id}")
    public void delete(@PathVariable Long id){

        log.debug("delete pathVariable:{}", id);
        todoEditor.delete(id);
    }

    @GetMapping("/api/feature-toggles")
    public FeatureTogglesProperties featureToggles() {

        log.debug("featureToggles:{}", featureTogglesProperties);
        return featureTogglesProperties;
    }

    private static class WriteTodoCommand {

        @Size(min = 4, max = 140)
        private String title;

        private Boolean completed;

        public String getTitle() {

            return title;
        }

        public void setTitle(String title) {

            this.title = title;
        }

        public Boolean getCompleted() {

            return completed;
        }

        public void setCompleted(Boolean completed) {

            this.completed = completed;
        }

        @Override
        public String toString() {

            return "WriteTodoCommand{" +
                    "title='" + title + '\'' +
                    ", completed=" + completed +
                    '}';
        }
    }
}
