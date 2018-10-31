package ru.yusdm.training.springboottest.passport.repo.impl;

import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;

@Repository
public class PassportRepoImpl implements PassportRepo {
    @Override
    public Passport findById(Long id) {
        return new Passport(id, id, "Test");
    }

    @Override
    public Passport findByUserId(Long userId) {
        return new Passport(userId, userId, "Test");
    }
}
