package ru.yusdm.training.springboottest.user.service.impl.springboottest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;
import ru.yusdm.training.springboottest.user.service.impl.UserServiceImpl;

import javax.annotation.PostConstruct;

/**
 * We use TestConfiguration annotation,
 * if we will use simple @Configuration, then
 * <p>
 * DemoApplicationTests.class will load context,
 * will find bean with naem = 'userServiceImpl',
 * later will find bean in testConfiguration class
 * UserServiceImplBootTestConfig, and its name is 'userServiceBean'
 * <p>
 * So there two beans in context 'userServiceImpl' and 'userServiceBean'
 * So Autowire in UserFacade will fail
 * <p>
 * But using @TestConfiguration this configuration will be skipped while load context!
 * <p>
 * We can omit @SpringBootTestConfiguration but in that case we can't read from
 * application-test.yml file. Because boot can parse yml, and simple spring only properties.
 * <p>
 * So if you don't need yml's or any other SpringBoot autoConfigurated beans
 * use simple tests.
 */
@TestConfiguration
public class UserServiceImplBootTestConfig {

    @Bean
    public UserService userServiceBean(UserRepo userRepo, PassportRepo passportRepo) {
        UserService userService = new UserServiceImpl(userRepo, passportRepo);
        System.out.println("In test config " + userService.hashCode());
        return userService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("This is post construct in UserServiceImplTestConfig (Boot config)");
    }
}
