package ru.yusdm.training.springboottest.user.service.impl.springboottest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yusdm.training.springboottest.BaseUnitTest;
import ru.yusdm.training.springboottest.child.repo.ChildJpaSpringDataRepo;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceA;
import ru.yusdm.training.springboottest.otherservices.NotUsedServiceB;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.repo.UserJpaSpringDataRepo;
import ru.yusdm.training.springboottest.user.repo.UserRepo;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper.appendChildrenToUser;
import static ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper.createUserWithoutChildren;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceImplBootTestConfig.class})
@MockBean(value = {NotUsedServiceA.class, NotUsedServiceB.class})
public class UserServiceImplBootTest extends BaseUnitTest {

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
        long userSearchId = 1L;
        User expected = createUserWithoutChildren(userSearchId);
        appendChildrenToUser(expected, 3);

        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.of(expected));
        when(childJpaSpringDataRepo.findByUser_Id(userSearchId)).thenReturn(expected.getChildren());

        User actual = userService.findById(userSearchId).orElse(EMPTY_USER);
        verify(childJpaSpringDataRepo, Mockito.times(1)).findByUser_Id(userSearchId);
        assertReflectionEquals(expected, actual);
    }

    @Test
    public void findByIdWithoutChildren() {
        long userSearchId = 1L;
        User expected = createUserWithoutChildren(userSearchId);

        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.of(expected));
        when(childJpaSpringDataRepo.findByUser_Id(userSearchId)).thenReturn(null);

        User actual = userService.findById(userSearchId).orElse(EMPTY_USER);
        assertReflectionEquals(expected, actual);
    }

    @Test
    public void testNoUser() {
        Optional<User> expected = Optional.empty();

        long userSearchId = 1L;
        when(userJpaSpringDataRepo.findById(userSearchId)).thenReturn(Optional.empty());

        Optional<User> actual = userService.findById(userSearchId);
        verify(childJpaSpringDataRepo, Mockito.times(0)).findByUser_Id(userSearchId);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll() {
        User userWithoutChildren = createUserWithoutChildren(1L);
        User userWithChildren = createUserWithoutChildren(2L);
        appendChildrenToUser(userWithChildren, 3);
        List<User> expected = Arrays.asList(userWithoutChildren, userWithChildren);

        when(userRepo.findAll()).thenReturn(expected);
        List<User> actual = userService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetValidToken() {
        String actualValidToken = userService.getValidToken();
        System.out.println("Token in Boot test '" + actualValidToken + "'");
        assertTrue(!actualValidToken.isEmpty() && !"${auth.validToken}".equals(actualValidToken));
    }
}