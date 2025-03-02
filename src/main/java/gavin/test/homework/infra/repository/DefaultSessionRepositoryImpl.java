package gavin.test.homework.infra.repository;

import gavin.test.homework.infra.common.utils.LRUCache;
import gavin.test.homework.infra.dto.DefaultSession;
import gavin.test.homework.infra.dto.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultSessionRepositoryImpl implements SessionRepository, InitializingBean {

    @Value("${user.session.ttl:600}")
    private long sessionTTL;

    private LRUCache<String, Session> sessionCache;
    private Map<String, String> sessionUserMapping;

    @Override
    public Session getSessionByUserId(String userId) {

        Session session = sessionCache.get(userId);

        if(session == null) {
            session = new DefaultSession();
            ((DefaultSession) session).setSessionId(UUID.randomUUID().toString());
            ((DefaultSession) session).setUserId(userId);
            ((DefaultSession) session).setCreateDate(new Date());
            sessionCache.put(userId, session);
            sessionUserMapping.put(session.getSessionId(), session.getUserId());
        }

        return session;
    }

    @Override
    public Session getSessionBySessionId(String sessionId) {
        String userId = this.sessionUserMapping.get(sessionId);
        return sessionCache.get(userId);
    }

    @Override
    public boolean isValidSession(String sessionId) {

        String userId = this.sessionUserMapping.get(sessionId);
        Session session = sessionCache.get(userId);

        return session != null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sessionCache = new LRUCache<String, Session>(1000, sessionTTL);
        this.sessionUserMapping = new ConcurrentHashMap<>();
    }
}
