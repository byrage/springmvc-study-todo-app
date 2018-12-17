package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

import java.util.Objects;

@RestController
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/user/profile")
    public ResponseEntity<UserProfile> userProfile(UserSession userSession) {

        log.debug("userSession={}", userSession);
        return Objects.nonNull(userSession) ?
                ResponseEntity.ok(new UserProfile(userSession.getUser())) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
}
