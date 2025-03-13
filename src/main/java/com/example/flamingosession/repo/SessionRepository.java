package com.example.flamingosession.repo;


import com.example.flamingosession.domain.Session;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionRepository {
    Map<String, Session> allActiveSessions =new HashMap<>();
    public Session getSession(String sessionId){
        return allActiveSessions .get(sessionId);
    }
    public void putNewSession(Session session){
        allActiveSessions.put(session.getSessionId(),session);
    }
    public void removeCompletedGameSession(String sessionId){
        allActiveSessions.remove(sessionId);
    }
}
