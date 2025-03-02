package gavin.test.homework.config.interceptor;

import gavin.test.homework.infra.dto.Session;
import gavin.test.homework.infra.repository.SessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    public static final String SESSION_KEY = "sessionkey";
    public static final String REQ_ATTR_USER_ID = "reqUserId";


    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String sessionKey = request.getParameter(SESSION_KEY);

        if(sessionKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("NO AUTHORIZED");
            return false;
        }

        Session session = sessionRepository.getSessionBySessionId(sessionKey);

        if(session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("NO AUTHORIZED");
            return false;
        }

        request.setAttribute(REQ_ATTR_USER_ID, session.getUserId());
        return true;
    }
}
