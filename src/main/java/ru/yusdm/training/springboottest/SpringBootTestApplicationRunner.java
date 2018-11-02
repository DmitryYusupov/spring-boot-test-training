package ru.yusdm.training.springboottest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Component
@Profile(value = "test_jpa_correctness")
public class SpringBootTestApplicationRunner implements ApplicationRunner {

    private final UserService userService;

    public SpringBootTestApplicationRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user_1 = createTestUser("Ivan", "Ivan123", 2);
        User user_2 = createTestUser("Dmitry", "Dmitry567", 3);

        userService.saveUpdate(user_1);
        userService.saveUpdate(user_2);
    }

    private User createTestUser(String userName, String passportSerial, int childrenNumber) {
        User user = new User();
        user.setName(userName);

        Passport passport = new Passport();
        passport.setUser(user);
        passport.setSerialNumber(passportSerial);
        user.setPassport(passport);

        user.setChildren(IntStream.range(1, childrenNumber).mapToObj(i -> new Child(user, "Child_" + i)).collect(toList()));
        return user;
    }
}
