package todoapp.web.todos;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import todoapp.security.UserSession;

@Controller
public class UserController {

    private ResourceLoader resourceLoader;

    public UserController(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/user/profile-picture")
    public @ResponseBody
    Resource profilePicture(UserSession session) {
        return resourceLoader.getResource(session.getUser().getProfilePicture().getUri().toString());
    }
}
