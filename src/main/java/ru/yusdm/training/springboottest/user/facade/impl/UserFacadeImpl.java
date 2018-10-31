package ru.yusdm.training.springboottest.user.facade.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yusdm.training.springboottest.user.facade.UserFacade;
import ru.yusdm.training.springboottest.user.service.UserService;

@Service
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

}
