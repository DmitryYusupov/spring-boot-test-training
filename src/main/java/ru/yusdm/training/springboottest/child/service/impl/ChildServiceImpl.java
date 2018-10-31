package ru.yusdm.training.springboottest.child.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.child.repo.ChildJpaSpringDataRepo;
import ru.yusdm.training.springboottest.child.service.ChildService;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildJpaSpringDataRepo childJpaSpringDataRepo;

    @Override
    public List<Child> findAll() {
        return childJpaSpringDataRepo.findAll();
    }
}
