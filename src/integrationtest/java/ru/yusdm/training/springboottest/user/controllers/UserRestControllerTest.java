package ru.yusdm.training.springboottest.user.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.unitils.reflectionassert.ReflectionAssert;
import ru.yusdm.training.springboottest.BaseWebIntegrationTest;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.dto.UserDto;
import ru.yusdm.training.springboottest.user.dto.UserFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.service.UserService;
import ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest extends BaseWebIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFromDomainToDtoConverter userFromDomainToDtoConverter;

    @Test
    public void findAll() {
        User user1 = UserDomainTestCommonHelper.createUserWithoutChildren(null);
        User user2 = UserDomainTestCommonHelper.createUserWithoutChildren(null);
        List<User> users = Arrays.asList(user1, user2);
        UserDomainTestCommonHelper.appendChildrenToUser(user2, 3);
        userService.saveUpdate(users);

        String url = createURLWithPort(UserRestController.PATH + "/all");
        ResponseEntity<List<UserDto>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<UserDto>>() {
                });


        List<UserDto> expected = users.stream().map(userFromDomainToDtoConverter::convert).collect(toList());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        ReflectionAssert.assertReflectionEquals(expected, response.getBody());
    }

    @Test
    public void findByIdNotFound() {
    }

    @Test
    public void findByIdWithChildren() {
    }

    @Test
    public void findByIdWithoutChildren() {
    }

    private String createURLWithPort(String path) {
        return "http://localhost:" + port + context.getServletContext().getContextPath() + path;
    }

}
