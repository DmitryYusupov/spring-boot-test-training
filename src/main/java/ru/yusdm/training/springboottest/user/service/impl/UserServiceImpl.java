package ru.yusdm.training.springboottest.user.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PassportRepo passportRepo;

    @Value("${auth.validToken}")
    private String validToken;

    public UserServiceImpl(UserRepo userRepo,
                           PassportRepo passportRepo) {
        this.userRepo = userRepo;
        this.passportRepo = passportRepo;
    }

    @Override
    public User findById(long id) {
        User result = userRepo.findById(id);
        Passport passport = passportRepo.findByUserId(id);
        result.setPassport(passport);
        return result;
    }

    @Override
    public String getValidToken() {
        return validToken;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("This is post construct in UserServiceImpl. HashCode = '" + this.hashCode() + "'");
    }
}
