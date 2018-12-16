package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.AbstractView;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.web.model.SiteProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class TodoController {

    /*    private Environment env;

        public TodoController(Environment env) {

            this.env = env;
        }
    */
    private SiteProperties siteProperties;
    private TodoFinder todoFinder;

    public TodoController(SiteProperties siteProperties, TodoFinder todoFinder) {

        this.siteProperties = siteProperties;
        this.todoFinder = todoFinder;
    }

    @ModelAttribute("site")
    public SiteProperties getSiteProperties() {

        return siteProperties;
    }

    /*
             1. ModelAndView (default)
             2. return String + Model params
             3. return void + Model params (CoC)
            */
    @RequestMapping("/todos")
    public void todos(Model model) {

        model.addAttribute("todos", todoFinder.getAll());
    }

    @Component("todos")
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
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

            log.debug("CSV 컨텐츠를 출력합니다.");

            String fileName = "attachment; filename=\"todos.csv\"";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, fileName);

            String header = "id,title,completed";
            response.getWriter().println(header);

            List<Todo> todos = (List<Todo>) model.getOrDefault("todos", Collections.emptyList());
            for (Todo todo : todos) {
                response.getWriter().println(String.format("%d,%s,%s",
                                                           todo.getId(), todo.getTitle(), todo.isCompleted()));
            }

            response.flushBuffer();
        }
    }
}
