package ru.yusdm.training.springboottest.user.service;


import ru.yusdm.training.springboottest.user.domain.User;

public interface UserService {
    User findById(long id);
    String getValidToken();
}
