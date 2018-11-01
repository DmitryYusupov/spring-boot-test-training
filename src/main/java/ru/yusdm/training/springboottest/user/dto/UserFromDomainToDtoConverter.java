package ru.yusdm.training.springboottest.user.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.child.dto.ChildFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.dto.PassportFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.user.domain.User;

import java.util.List;

import static java.util.stream.Collectors.toList;


@AllArgsConstructor
@Component
public class UserFromDomainToDtoConverter implements Converter<User, UserDto> {

    private final ChildFromDomainToDtoConverter childFromDomainToDtoConverter;
    private final PassportFromDomainToDtoConverter passportFromDomainToDtoConverter;

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassportDto(passportFromDomainToDtoConverter.convert(user.getPassport()));

        if (user.getChildren() != null && !user.getChildren().isEmpty()) {
            List<ChildDto> children = user.getChildren().stream().map(childFromDomainToDtoConverter::convert).collect(toList());
            userDto.setChildren(children);
        }

        return userDto;
    }
}