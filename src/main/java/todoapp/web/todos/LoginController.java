package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.UserEntityNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserPasswordVerifier userPasswordVerifier;
    private UserJoinder userJoinder;

    public LoginController(UserPasswordVerifier userPasswordVerifier, UserJoinder userJoinder) {

        this.userPasswordVerifier = userPasswordVerifier;
        this.userJoinder = userJoinder;
    }

    @GetMapping("/login")
    public String loginForm() {

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginCommand loginCommand) {

        log.debug("loginCommand={}, bindingResult={}", loginCommand);

        /*
        수동으로 스프링으로부터 에러를 가져와서 처리하는 방식
        bindingResult를 파라미터로 받을 경우 스프링에서 따로 후처리를 안해줌

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("message", "login error");
            return "login";
        }
        */

        try {
            userPasswordVerifier.verify(loginCommand.getUsername(), loginCommand.getPassword());
        } catch (UserEntityNotFoundException notFoundException) {
            userJoinder.join(loginCommand.getUsername(), loginCommand.getPassword());
        }

        return "redirect:/todos";
    }

    // LoginController 스코프에만 유효한 방식
    // 전체 컨트롤러에 적용할때는 에러처리 클래스 생성후 @ControllerAdvice 어노테이션 추가
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException error, Model model) {
        model.addAttribute("bindingResult", error.getBindingResult());
        model.addAttribute("message", "사용자 이름 또는 비밀번호가 값이 올바르지 않습니다.");

        return "login";
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
