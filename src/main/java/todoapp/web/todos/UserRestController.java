package todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
@RolesAllowed(UserSession.ROLE_USER)
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ProfilePictureChanger profilePictureChanger;

    public UserRestController(ProfilePictureChanger profilePictureChanger) {

        this.profilePictureChanger = profilePictureChanger;
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity<UserProfile> userProfile(UserSession userSession) {

        log.debug("userSession={}", userSession);
        return Objects.nonNull(userSession) ?
                ResponseEntity.ok(new UserProfile(userSession.getUser())) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/api/user/profile-picture")
    public UserProfile profilePicture(UserSession session, MultipartFile profilePicture) throws IOException {

        // 어떻게 파일에 저장할지 추상화로 구현할수 있음
        // TODO : ProfilePictureStorage을 이용해서 구현해보세요
        Path basePath = Paths.get("./files/user-profile-picture");
        if (!basePath.toFile().exists()) {
            basePath.toFile().mkdirs();
        }

        // 현재 경로로부터 새로운 파일 경로를 얻어온다.
        Path filePath = basePath.resolve(UUID.randomUUID().toString());
        log.debug("filePath={}", filePath);
        profilePicture.transferTo(filePath);

        User savedUser = profilePictureChanger.change(session.getUser().getUsername(), new ProfilePicture(filePath.toUri()));

        RequestContextHolder.currentRequestAttributes()
                            .setAttribute(User.class.toString(), savedUser, RequestAttributes.SCOPE_SESSION);

        return new UserProfile(savedUser);
    }
}
