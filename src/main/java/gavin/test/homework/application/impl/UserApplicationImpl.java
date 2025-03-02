package gavin.test.homework.application.impl;

import gavin.test.homework.application.UserApplication;
import gavin.test.homework.infra.dto.Session;
import gavin.test.homework.infra.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserApplicationImpl implements UserApplication {

    @Autowired
    private SessionRepository sessionRepository;


    @Override
    public String login(String userId) {
        Session session = sessionRepository.getSessionByUserId(userId);
        return session.getSessionId();
    }
}
