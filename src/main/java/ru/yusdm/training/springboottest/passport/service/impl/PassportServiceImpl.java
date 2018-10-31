package ru.yusdm.training.springboottest.passport.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.passport.repo.PassportSpringDataRepo;
import ru.yusdm.training.springboottest.passport.service.PassportService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepo passportRepo;
    private final PassportSpringDataRepo passportSpringDataRepo;

    @Override
    public Optional<Passport> findById(Long id) {
        return passportSpringDataRepo.findById(id);
    }

    @Override
    public List<Passport> findAll() {
        return passportRepo.findAll();
    }

}
