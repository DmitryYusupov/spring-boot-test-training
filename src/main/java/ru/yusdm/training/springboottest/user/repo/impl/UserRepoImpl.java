package ru.yusdm.training.springboottest.user.repo.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserRepo;

import javax.annotation.PostConstruct;

@Repository
public class UserRepoImpl implements UserRepo {

    @Value("${connection.url}")
    private String connectionUrl;

    @Override
    public User findById(long id) {
        return new User(id, "Test");
    }

    @PostConstruct
    public Object createConnection() {
        System.out.println("USerRepo: Create connection using url " + connectionUrl);
        return "New connection";
    }
}
