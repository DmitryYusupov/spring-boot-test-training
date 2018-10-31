package ru.yusdm.training.springboottest.user.service.impl.springboottest;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.yusdm.training.springboottest.BaseTest;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest(classes = {UserServiceImplBootTestConfig.class})
public class UserServiceImplBootTest extends BaseTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PassportRepo passportRepo;

    @Autowired
    private UserService userService;

    @Test
    public void findById() {
        Passport userPassport = new Passport(1L, 1L, "PassportSerial");
        User expected = new User(1L, "UserName", userPassport);
        Mockito.when(userRepo.findById(1)).then((Answer<User>) invocation -> expected);
        User fact = userService.findById(1L);
        assertEquals(expected, fact);
    }

    @Test
    public void testGetValidToken() {
        String factValidToken = userService.getValidToken();
        System.out.println("Token in Boot test '" + factValidToken + "'");
        assertTrue(!factValidToken.isEmpty() && !"${auth.validToken}".equals(factValidToken));
    }
}