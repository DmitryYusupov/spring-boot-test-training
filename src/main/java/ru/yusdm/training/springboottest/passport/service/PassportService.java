package ru.yusdm.training.springboottest.passport.service;

import ru.yusdm.training.springboottest.passport.domain.Passport;

public interface PassportService {
    Passport findById(Long id);

    Passport findByUserId(Long userId);
}
