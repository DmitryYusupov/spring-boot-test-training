package ru.yusdm.training.springboottest.child.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yusdm.training.springboottest.child.domain.Child;

import java.util.List;

@Repository
public interface ChildJpaSpringDataRepo extends JpaRepository<Child, Long> {

    List<Child> findByUser_Id(long userId);

}
