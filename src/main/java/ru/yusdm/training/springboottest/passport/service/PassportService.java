package ru.yusdm.training.springboottest.passport.service;

import ru.yusdm.training.springboottest.passport.domain.Passport;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    Optional<Passport> findById(Long id);

    List<Passport> findAll();

}
