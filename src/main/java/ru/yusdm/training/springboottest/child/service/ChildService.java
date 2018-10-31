package ru.yusdm.training.springboottest.child.service;

import ru.yusdm.training.springboottest.child.domain.Child;

import java.util.List;

public interface ChildService {
    List<Child> findAll();
}
