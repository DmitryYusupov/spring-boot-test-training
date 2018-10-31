package ru.yusdm.training.springboottest.passport.repo;

import ru.yusdm.training.springboottest.passport.domain.Passport;

import java.util.List;

public interface PassportRepo {
    List<Passport> findAll();
}
