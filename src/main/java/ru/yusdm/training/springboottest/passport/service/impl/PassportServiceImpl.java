package ru.yusdm.training.springboottest.passport.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.passport.service.PassportService;

@Service
@AllArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepo passportRepo;

    @Override
    public Passport findById(Long id) {
        return passportRepo.findById(id);
    }

    @Override
    public Passport findByUserId(Long userId) {
        return passportRepo.findByUserId(userId);
    }
}
