package ru.yusdm.training.springboottest.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.domain.UserFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.user.dto.UserDto;
import ru.yusdm.training.springboottest.user.dto.UserFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static ru.yusdm.training.springboottest.user.controllers.UserRestController.PATH;
import static ru.yusdm.training.springboottest.user.exceptions.Exceptions.ERROR_500_WHILE_DELETE;
import static ru.yusdm.training.springboottest.user.exceptions.Exceptions.ERROR_500_WHILE_INSERT;

@RestController
@RequestMapping(PATH)
@AllArgsConstructor
public class UserRestController {

    static final String PATH = "/api/user";

    private final UserService userService;
    private final UserFromDomainToDtoConverter userFromDomainToDtoConverter;
    private final UserFromDtoToDomainConverter userFromDtoToDomainConverter;


    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(userFromDomainToDtoConverter::convert).collect(toList());
    }

    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.of(userService.findById(id));
    }

    @GetMapping("/alldomains")
    public List<User> findAllDomains() {
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        try {
            User user = userFromDtoToDomainConverter.convert(userDto);
            user = userService.saveUpdate(user);
            return ResponseEntity.ok(userFromDomainToDtoConverter.convert(user));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ERROR_500_WHILE_INSERT.getErrorMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ERROR_500_WHILE_DELETE.getErrorMessage());
        }
    }
}

