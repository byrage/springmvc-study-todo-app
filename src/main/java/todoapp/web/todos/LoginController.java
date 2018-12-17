package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;
import todoapp.core.user.domain.User;
import todoapp.web.model.FeatureTogglesProperties;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String loginForm() {

        return "login";
    }

    @PostMapping("/login")
    public void login(@Valid LoginCommand loginCommand) {

        log.debug("loginCommand={}", loginCommand);
    }

    public static class LoginCommand {

        @Size(min = 4, max = 20)
        private String username;
        @Size(min = 8)
        private String password;

        public String getUsername() {

            return username;
        }

        public void setUsername(String username) {

            this.username = username;
        }

        public String getPassword() {

            return password;
        }

        public void setPassword(String password) {

            this.password = password;
        }

        @Override
        public String toString() {

            return "LoginCommand{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}
