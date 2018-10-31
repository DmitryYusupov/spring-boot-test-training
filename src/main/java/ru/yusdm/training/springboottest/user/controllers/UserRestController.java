package ru.yusdm.training.springboottest.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yusdm.training.springboottest.user.domain.User;
import ru.yusdm.training.springboottest.user.dto.UserDto;
import ru.yusdm.training.springboottest.user.dto.UserFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.exceptions.Exceptions;
import ru.yusdm.training.springboottest.user.service.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.yusdm.training.springboottest.user.controllers.UserRestController.PATH;

@RestController
@RequestMapping(value = PATH)
@AllArgsConstructor
public class UserRestController {

    public static final String PATH = "api/user";

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(UserFromDomainToDtoConverter.INSTANCE::convert).collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.of(userService.findById(id));
    }

    @GetMapping("/alldomains")
    public List<User> test() {
        return userService.findAll();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        try {
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Exceptions.ERROR_500_WHILE_UPDATE.getErrorMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        try {
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Exceptions.ERROR_500_WHILE_INSERT.getErrorMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Exceptions.ERROR_500_WHILE_DELETE.getErrorMessage());
        }
    }
}

