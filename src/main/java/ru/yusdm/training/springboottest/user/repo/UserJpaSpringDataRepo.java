package ru.yusdm.training.springboottest.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.user.domain.User;

@Repository
public interface UserJpaSpringDataRepo extends JpaRepository<User, Long> {
}
