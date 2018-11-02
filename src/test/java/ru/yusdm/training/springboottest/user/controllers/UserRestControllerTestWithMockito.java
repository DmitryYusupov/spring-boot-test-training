package ru.yusdm.training.springboottest.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yusdm.training.springboottest.BaseUnitTest;
import ru.yusdm.training.springboottest.child.domain.ChildFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.child.dto.ChildFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.passport.domain.PassportFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.passport.dto.PassportDto;
import ru.yusdm.training.springboottest.passport.dto.PassportFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.domain.UserFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.user.dto.UserDto;
import ru.yusdm.training.springboottest.user.dto.UserFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yusdm.training.springboottest.user.controllers.UserRestController.PATH;
import static ru.yusdm.training.springboottest.user.exceptions.Exceptions.ERROR_500_WHILE_DELETE;
import static ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper.appendChildrenToUser;
import static ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper.appendChildrenToUserSettingId;
import static ru.yusdm.training.springboottest.user.service.impl.common.UserDomainTestCommonHelper.createUserWithoutChildren;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserRestControllerTestWithMockito extends BaseUnitTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ChildFromDomainToDtoConverter childFromDomainToDtoConverter;

    @MockBean
    private UserFromDomainToDtoConverter userFromDomainToDtoConverter;

    @MockBean
    private UserFromDtoToDomainConverter userFromDtoToDomainConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAll() throws Exception {
        Long userId = 1L;
        User user1 = createUserWithoutChildren(userId++);
        appendChildrenToUserSettingId(user1, 2, 1);

        User user2 = createUserWithoutChildren(userId);
        appendChildrenToUserSettingId(user2, 3, 10);
        List<User> users = Arrays.asList(user1, user2);

        String expected = "[{\"id\":1,\"name\":\"UserName\"," +
                "\"passportDto\":{\"id\":null,\"serialNumber\":\"passportSerial\"}," +

                "\"children\":" +
                "[{\"id\":1,\"userId\":1,\"name\":\"Child_1\"}," +
                "{\"id\":2,\"userId\":1,\"name\":\"Child_2\"}]}," +
                "" +
                "{\"id\":2,\"name\":\"UserName\"," +
                "\"passportDto\":{\"id\":null,\"serialNumber\":\"passportSerial\"}," +
                "\"children\":" +
                "[{\"id\":10,\"userId\":2,\"name\":\"Child_1\"}," +
                "{\"id\":11,\"userId\":2,\"name\":\"Child_2\"}," +
                "{\"id\":12,\"userId\":2,\"name\":\"Child_3\"}]}]";

        when(userService.findAll()).thenReturn(users);
        when(userFromDomainToDtoConverter.convert(user1)).thenReturn(getUserFromDomainToDtoConverter().convert(user1));
        when(userFromDomainToDtoConverter.convert(user2)).thenReturn(getUserFromDomainToDtoConverter().convert(user2));
        this.mockMvc.perform(get(PATH + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        verify(userService, times(1)).findAll();
    }

    private UserFromDomainToDtoConverter getUserFromDomainToDtoConverter() {
        return new UserFromDomainToDtoConverter(new ChildFromDomainToDtoConverter(), new PassportFromDomainToDtoConverter());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        long id = 1L;

        when(userService.findById(id)).thenReturn(Optional.empty());
        this.mockMvc.perform(get(PATH + "/" + id))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findById(id);
    }

    @Test
    public void findByIdWithChildren() throws Exception {
        long id = 1L;
        User user = createUserWithoutChildren(id);
        appendChildrenToUserSettingId(user, 2, 1);

        when(userService.findById(id)).thenReturn(Optional.of(user));
        String expected =
                "{\"id\":1,\"name\":\"UserName\"," +
                        "\"passport\":{\"id\":null,\"serialNumber\":\"passportSerial\"}," +
                        "\"children\":" +
                        "[{\"id\":1,\"name\":\"Child_1\"}," +
                        "{\"id\":2,\"name\":\"Child_2\"}]}";

        this.mockMvc.perform(get(PATH + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expected));
        verify(userService, times(1)).findById(id);
    }

    @Test
    public void findByIdWithoutChildren() throws Exception {
        long id = 1L;

        User user = createUserWithoutChildren(id);
        user.getPassport().setId(1L);
        when(userService.findById(id)).thenReturn(Optional.of(user));
        String expected =
                "{\"id\":1,\"name\":\"UserName\","
                        + "\"passport\":{\"id\":1,\"serialNumber\":\"passportSerial\"}," +
                        "\"children\":null}";
        this.mockMvc.perform(get(PATH + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(expected));
        verify(userService, times(1)).findById(id);
    }


    @Test
    public void saveWithChildren() throws Exception {
        UserDto userDto = createUserDtoWithPassport(null);
        appendChildrenToDtoUser(userDto, 3);

        //we rely on library - don't do it. Provide string! (Lazy to write for me)
        ObjectMapper mapper = new ObjectMapper();
        String dtoStr = mapper.writeValueAsString(userDto);

        User userDomain = getUserFromDtoToDomainConverter().convert(userDto);
        when(userFromDtoToDomainConverter.convert(any(UserDto.class))).thenReturn(userDomain);
        when(userService.saveUpdate(userDomain)).thenReturn(
                ((Supplier<User>) () -> {
                    userDomain.setId(1L);
                    return userDomain;
                }
                ).get());
        when(userFromDomainToDtoConverter.convert(any(User.class))).thenReturn(userDto);

        String expected =
                "{" +
                        "\"id\":null,\"name\":\"userName\"," +

                        "\"passportDto\":{\"id\":null,\"serialNumber\":\"serialNumber\"}," +

                        "\"children\":" +
                        "[" +
                        "{\"id\":null,\"userId\":null,\"name\":\"ChildName_1\"}," +
                        "{\"id\":null,\"userId\":null,\"name\":\"ChildName_2\"}," +
                        "{\"id\":null,\"userId\":null,\"name\":\"ChildName_3\"}" +
                        "]" +

                        "}";
        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoStr))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

    }

    @Test
    public void saveWithoutChildren() throws Exception {
        UserDto userDto = createUserDtoWithPassport(null);
        //  String dtoStr = "{\"id\":null,\"name\":\"userName\",\"passportDto\":{\"id\":null,\"serialNumber\":\"serialNumber\"},\"children\":null}";
        ObjectMapper mapper = new ObjectMapper();
        String dtoStr = mapper.writeValueAsString(userDto);

        User userDomain = getUserFromDtoToDomainConverter().convert(userDto);
        when(userFromDtoToDomainConverter.convert(any(UserDto.class))).thenReturn(userDomain);
        when(userService.saveUpdate(userDomain)).thenReturn(
                ((Supplier<User>) () -> {
                    userDomain.setId(1L);
                    return userDomain;
                }
                ).get());
        when(userFromDomainToDtoConverter.convert(any(User.class))).thenReturn(getUserFromDomainToDtoConverter().convert(userDomain));

        String expected = "{\"id\":1,\"name\":\"userName\",\"passportDto\":{\"id\":null,\"serialNumber\":\"serialNumber\"},\"children\":null}";
        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoStr))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    private UserFromDtoToDomainConverter getUserFromDtoToDomainConverter() {
        return new UserFromDtoToDomainConverter(
                new PassportFromDtoToDomainConverter(), new ChildFromDtoToDomainConverter()
        );
    }

    private UserDto createUserDtoWithPassport(Long id) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName("userName");
        userDto.setPassportDto(new PassportDto(null, "serialNumber"));
        return userDto;
    }

    private void appendChildrenToDtoUser(UserDto user, int childrenNumber) {
        List<ChildDto> children = LongStream.rangeClosed(1, childrenNumber)
                .mapToObj(i -> new ChildDto(null, user.getId(), "ChildName_" + i))
                .collect(toList());
        user.setChildren(children);
    }

    @Test
    public void delete() throws Exception {
        long id = 1L;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + id + ""))
                .andExpect(status().isOk());
        verify(userService, times(1)).delete(id);
    }

    @Test
    public void deleteWithError() throws Exception {
        long id = 1L;

        String exceptionErrorMessage = ERROR_500_WHILE_DELETE.getErrorMessage();
        Mockito.doThrow(new RuntimeException(exceptionErrorMessage)).when(userService).delete(id);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + id + ""))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString(exceptionErrorMessage)));
        verify(userService, times(1)).delete(id);
    }

}