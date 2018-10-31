package ru.yusdm.training.springboottest.user.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.child.dto.ChildFromDomainToDtoConverter;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.dto.PassportDto;
import ru.yusdm.training.springboottest.user.domain.User;

import java.util.List;

import static java.util.stream.Collectors.toList;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFromDomainToDtoConverter implements Converter<User, UserDto> {

    public static final UserFromDomainToDtoConverter INSTANCE = new UserFromDomainToDtoConverter();

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassportDto(new PassportDto(user.getPassport().getId(), user.getPassport().getSerialNumber()));

        if (user.getChildren() != null && !user.getChildren().isEmpty()) {
            List<ChildDto> children = user.getChildren().stream().map(ChildFromDomainToDtoConverter.INSTANCE::convert).collect(toList());
            userDto.setChildren(children);
        }

        return userDto;
    }
}