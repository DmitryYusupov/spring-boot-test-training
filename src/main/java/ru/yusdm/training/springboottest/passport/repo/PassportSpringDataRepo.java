package ru.yusdm.training.springboottest.passport.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.passport.domain.Passport;

@Repository
public interface PassportSpringDataRepo extends JpaRepository<Passport, Long> {
}
