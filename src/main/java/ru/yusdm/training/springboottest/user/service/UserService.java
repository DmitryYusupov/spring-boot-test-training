package ru.yusdm.training.springboottest.user.service;


import ru.yusdm.training.springboottest.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);
    User saveUpdate(User user);
    List<User> saveUpdate(List<User> users);
    void delete(long id);
    String getValidToken();
    List<User> findAll();
}
