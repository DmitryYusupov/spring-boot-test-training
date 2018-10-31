package ru.yusdm.training.springboottest.passport.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PassportRepoImpl implements PassportRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<Passport> findAll() {
        TypedQuery<Passport> query = em.createQuery("FROM Passport", Passport.class);
        return query.getResultList();
    }
}
