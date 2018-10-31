package ru.yusdm.training.springboottest.passport.repo;

import ru.yusdm.training.springboottest.passport.domain.Passport;

public interface PassportRepo {
    Passport findById(Long id);
    Passport findByUserId(Long userId);
}
