package ru.yusdm.training.springboottest.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yusdm.training.springboottest.BaseTest;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.child.domain.ChildFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.child.dto.ChildFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.passport.domain.Passport;
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

@WebMvcTest
public class UserRestControllerTestWithMockito extends BaseTest {

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

        User user1 = createDomainUserWithPassport(1L);
        appendChildrenToDomainUser(user1, 2);

        User user2 = createDomainUserWithPassport(2L);
        appendChildrenToDomainUser(user2, 3);
        List<User> users = Arrays.asList(user1, user2);

        String expected = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"userName\",\n" +
                "    \"passportDto\": {\n" +
                "      \"id\": 1,\n" +
                "      \"serialNumber\": \"serailNumber\"\n" +
                "    },\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"userId\": 1,\n" +
                "        \"name\": \"Child_1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"userId\": 1,\n" +
                "        \"name\": \"Child_2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"userName\",\n" +
                "    \"passportDto\": {\n" +
                "      \"id\": 2,\n" +
                "      \"serialNumber\": \"serailNumber\"\n" +
                "    },\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"userId\": 2,\n" +
                "        \"name\": \"Child_1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"userId\": 2,\n" +
                "        \"name\": \"Child_2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"userId\": 2,\n" +
                "        \"name\": \"Child_3\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        when(userService.findAll()).thenReturn(users);
        when(userFromDomainToDtoConverter.convert(user1)).thenReturn(getUserFromDomainToDtoConverter().convert(user1));
        when(userFromDomainToDtoConverter.convert(user2)).thenReturn(getUserFromDomainToDtoConverter().convert(user2));
        this.mockMvc.perform(get(PATH + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));

        verify(userService, times(1)).findAll();
    }

    private UserFromDomainToDtoConverter getUserFromDomainToDtoConverter(){
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
        User user = createDomainUserWithPassport(id);
        appendChildrenToDomainUser(user, 2);

        when(userService.findById(id)).thenReturn(Optional.of(user));
        String expected =
                "{\"id\":1,\"name\":\"userName\"," +
                        "\"passport\":{\"id\":1,\"serialNumber\":\"serailNumber\"}," +
                        "\"children\":[{\"id\":1,\"name\":\"Child_1\"},{\"id\":2,\"name\":\"Child_2\"}]}";

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

        when(userService.findById(id)).thenReturn(Optional.of(createDomainUserWithPassport(1L)));
        String expected =
                "{\"id\":1,\"name\":\"userName\","
                        + "\"passport\":{\"id\":1,\"serialNumber\":\"serailNumber\"}," +
                        "\"children\":null}";
        this.mockMvc.perform(get(PATH + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(expected));
        verify(userService, times(1)).findById(id);
    }

    private User createDomainUserWithPassport(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("userName");

        Passport passport = new Passport();
        passport.setUser(user);
        passport.setId(user.getId());
        passport.setSerialNumber("serailNumber");

        user.setPassport(passport);
        return user;
    }

    private void appendChildrenToDomainUser(User user, int childrenNumber) {
        List<Child> children = LongStream.rangeClosed(1, childrenNumber).mapToObj(i -> {
            Child child = new Child();
            child.setName("Child_" + i);
            child.setUser(user);
            child.setId(i);
            return child;
        }).collect(toList());
        user.setChildren(children);
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