package ru.yusdm.training.springboottest.user.service.impl.plainoldtest;


import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.yusdm.training.springboottest.BaseTest;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.child.repo.ChildJpaSpringDataRepo;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceA;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceB;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserJpaSpringDataRepo;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImplPlainOldTestConfig.class})
@MockBean(value = {NotUsedServiceA.class, NotUsedServiceB.class})
public class UserServiceImplPlainOldTest extends BaseTest {

    private static final User EMPTY_USER = new User();

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserJpaSpringDataRepo userJpaSpringDataRepo;

    @MockBean
    private ChildJpaSpringDataRepo childJpaSpringDataRepo;

    @Autowired
    private UserService userService;

    @Test
    public void findByIdWithChildren() {
        User expected = createUserWithoutChildren();
        appendChildrenToUser(expected);

        long userSearchId = 1L;
        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.of(createUserWithoutChildren()));
        when(childJpaSpringDataRepo.findByUser_Id(userSearchId)).thenReturn(expected.getChildren());


        User fact = userService.findById(userSearchId).orElse(EMPTY_USER);
        verify(childJpaSpringDataRepo, Mockito.times(1)).findByUser_Id(userSearchId);
        assertEquals(expected, fact);
    }

    @Test
    public void findByIdWithoutChildren() {
        User expected = createUserWithoutChildren();

        long userSearchId = 1L;
        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.of(createUserWithoutChildren()));
        when(childJpaSpringDataRepo.findByUser_Id(userSearchId)).thenReturn(Collections.EMPTY_LIST);

        User fact = userService.findById(userSearchId).orElse(EMPTY_USER);
        assertEquals(expected, fact);
    }

    @Test
    public void testNoUser() {
        Optional<User> expected = Optional.empty();

        long userSearchId = 1L;
        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.empty());

        Optional<User> fact = userService.findById(userSearchId);
        verify(childJpaSpringDataRepo, Mockito.times(0)).findByUser_Id(userSearchId);
        assertEquals(expected, fact);
    }

    @Test
    public void testFindAll() {
        User userWithoutChildren = createUserWithoutChildren();
        User userWithChildren = createUserWithoutChildren();
        appendChildrenToUser(userWithChildren);
        List<User> expected = Arrays.asList(userWithoutChildren, userWithChildren);

        when(userRepo.findAll()).thenReturn(expected);
        List<User> fact = userService.findAll();
        assertEquals(expected, fact);
    }


    private User createUserWithoutChildren() {
        User user = new User();
        user.setPassport(createUserPassport(user));
        user.setId(1L);
        user.setName("UserName");
        return user;
    }

    private void appendChildrenToUser(User user) {
        List<Child> children = IntStream.range(1, 3).mapToObj(i -> new Child(user, "Child_" + i)).collect(toList());
        user.setChildren(children);
    }

    private Passport createUserPassport(User user) {
        return new Passport(1L, user, "passportSerial");
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
