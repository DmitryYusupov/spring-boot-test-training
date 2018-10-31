package ru.yusdm.training.springboottest.user.service.impl.plainoldtest;


import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceA;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceB;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.repo.PassportRepo;
import ru.yusdm.training.springboottest.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.yusdm.training.springboottest.BaseTest;

import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
@ContextConfiguration(classes = {UserServiceImplPlainOldTestConfig.class})
@MockBean(value = {NotUsedServiceA.class, NotUsedServiceB.class})
public class UserServiceImplPlainOldTest extends BaseTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PassportRepo passportRepo;

    @Autowired
    private UserService userService;

    @Test
    public void findById() {
/*        User expected = new User(1L, "UserName", new Passport(1L, 1L, "PassportSerial"));
        Mockito.when(userRepo.findById(1)).then((Answer<User>) invocation -> expected);
        User fact = userService.findById(1L);
        assertEquals(expected, fact);*/
    }

    @Test
    public void testGetValidToken() {
        String factValidToken = userService.getValidToken();
        System.out.println("Token in PlainOld test '" + factValidToken + "'");
        System.out.println("Token hasn't taken from 'application-test.yml'!");

        assertTrue(!factValidToken.isEmpty());
        assertEquals("${auth.validToken}", factValidToken);
    }

}
