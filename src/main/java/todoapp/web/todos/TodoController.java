package todoapp.web.todos;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.AbstractView;

import todoapp.commons.domain.Spreadsheet;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.model.FeatureTogglesProperties;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TodoFinder todoFinder;

    public TodoController(TodoFinder todoFinder) {

        this.todoFinder = todoFinder;
    }

    // viewName: todos
    // classpath:/tem../todos.html
    @RequestMapping("/todos")
    public void todos() {

    }

    @RequestMapping(value = "/todos", produces = "text/csv")
    public Spreadsheet downloadTodos() {
        List<Todo> todos = todoFinder.getAll();

        Spreadsheet.Row header = new Spreadsheet.Row()
                .addCell("id")
                .addCell("title")
                .addCell("complated");
        List<Spreadsheet.Row> rows = todos.stream().map(todo -> new Spreadsheet.Row()
                .addCell(todo.getId())
                .addCell(todo.getTitle())
                .addCell(todo.isCompleted()))
                                          .collect(Collectors.toList());

        return new Spreadsheet("todos", header, rows);
    }


    // @Component("todos")
    public static class TodoCsvView extends AbstractView {

        private final Logger log = LoggerFactory.getLogger(this.getClass());

        public TodoCsvView() {
            setContentType("text/csv");
        }

        @Override
        protected boolean generatesDownloadContent() {
            return true;
        }

        @Override
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
            log.debug("TodoCsvView: CSV 컨텐츠를 출력합니다.");

            String fileName = "attachment; filename=\"todos.csv\"";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, fileName);

            String header = "id,titile,complated";
            response.getWriter().println(header);

            // 모델로부터 할 일 목록을 추출해서, CSV 방식으로 출력하기
            List<Todo> todos = (List<Todo>) model.getOrDefault("todos", Collections.emptyList());
            for (Todo todo : todos) {
                String line = String.format("%d,%s,%s", todo.getId(), todo.getTitle(), todo.isCompleted());
                response.getWriter().println(line);
            }

            response.flushBuffer();
        }

    }

}