package gavin.test.homework.infra.repository;

import gavin.test.homework.infra.dto.Session;

public interface SessionRepository {

    Session getSessionByUserId(String userId);
    Session getSessionBySessionId(String sessionId);
    boolean isValidSession(String sessionId);
}
