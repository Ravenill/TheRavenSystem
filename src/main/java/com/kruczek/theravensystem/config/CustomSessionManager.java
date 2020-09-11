package com.kruczek.theravensystem.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class CustomSessionManager {

    @PersistenceContext
    private EntityManager entityManager;

    public Session getSession() {
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }

        return session;
    }
}
