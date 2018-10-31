package ru.yusdm.training.springboottest.user.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yusdm.training.springboottest.child.repo.ChildJpaSpringDataRepo;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceA;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceB;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserJpaSpringDataRepo;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserJpaSpringDataRepo userJpaSpringDataRepo;
    private final ChildJpaSpringDataRepo childJpaSpringDataRepo;

    private final NotUsedServiceA notUsedServiceA;
    private final NotUsedServiceB notUsedServiceB;

    @Value("${auth.validToken}")
    private String validToken;

    public UserServiceImpl(UserRepo userRepo,
                           UserJpaSpringDataRepo userJpaSpringDataRepo,
                           ChildJpaSpringDataRepo childJpaSpringDataRepo,
                           NotUsedServiceA notUsedServiceA,
                           NotUsedServiceB notUsedServiceB) {
        this.userRepo = userRepo;
        this.userJpaSpringDataRepo = userJpaSpringDataRepo;
        this.childJpaSpringDataRepo = childJpaSpringDataRepo;
        this.notUsedServiceA = notUsedServiceA;
        this.notUsedServiceB = notUsedServiceB;
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> optional = userJpaSpringDataRepo.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            user.getPassport();
            user.setChildren(childJpaSpringDataRepo.findByUser_Id(user.getId()));
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User saveUpdate(User user) {
        return userJpaSpringDataRepo.save(user);
    }

    @Override
    public void delete(long id) {
        userJpaSpringDataRepo.deleteById(id);
    }

    @Override
    public String getValidToken() {
        return validToken;
    }


    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("This is post construct in UserServiceImpl. HashCode = '" + this.hashCode() + "'");
    }
}
