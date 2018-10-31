package ru.yusdm.training.springboottest.user.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserRepo;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    @Autowired
    private EntityManager em;

    @Value("${connection.url}")
    private String connectionUrl;

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("USerRepo: Create connection using url " + connectionUrl);
    }

}
