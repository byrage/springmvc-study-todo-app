package todoapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import todoapp.core.user.domain.User;

import java.util.Objects;

public class UserSessionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return UserSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        User loginedUser = (User) webRequest.getAttribute(User.class.toString(),
                                                          RequestAttributes.SCOPE_SESSION);

        log.debug("loginedUser={}", loginedUser);
        if (Objects.nonNull(loginedUser)) {
            return new UserSession(loginedUser);
        }

        return null;
    }
}
