package ru.yusdm.training.springboottest.user.repo;


import ru.yusdm.training.springboottest.user.domain.User;

import java.util.List;

public interface UserRepo {
    List<User> findAll();
}
