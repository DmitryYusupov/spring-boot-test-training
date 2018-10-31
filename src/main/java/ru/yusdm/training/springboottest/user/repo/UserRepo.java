package ru.yusdm.training.springboottest.user.repo;


import ru.yusdm.training.springboottest.user.domain.User;

public interface UserRepo {
    User findById(long id);
}
