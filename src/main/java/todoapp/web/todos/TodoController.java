package todoapp.web.todos;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    /*    private Environment env;

        public TodoController(Environment env) {

            this.env = env;
        }
    */
    private SiteProperties siteProperties;

    public TodoController(SiteProperties siteProperties) {

        this.siteProperties = siteProperties;
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
    public void todos() {
    }
}
