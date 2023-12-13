package com.sotska.aspect;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CleanCacheAspect {

    @Autowired
    private EntityManager em;

    @Before("execution(* org.springframework.data.jpa.repository.JpaRepository.findAll(..))")
    public void clearCache() {
        var session = (Session) em.getDelegate();
        session.clear();
    }
}
