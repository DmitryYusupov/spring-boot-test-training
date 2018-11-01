package ru.yusdm.training.springboottest.user.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.child.domain.ChildFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.domain.PassportFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserFromDtoToDomainConverter implements Converter<UserDto, User> {

    private final PassportFromDtoToDomainConverter passportFromDtoToDomainConverter;
    private final ChildFromDtoToDomainConverter childFromDtoToDomainConverter;

    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        if (userDto.getPassportDto() != null) {
            Passport passport = passportFromDtoToDomainConverter.convert(userDto.getPassportDto());
            passport.setUser(user);
            user.setPassport(passport);
        }

        if (userDto.getChildren() != null && !userDto.getChildren().isEmpty()) {
            List<Child> children = userDto.getChildren().stream().map(dto -> {
                Child child = childFromDtoToDomainConverter.convert(dto);
                child.setUser(user);
                return child;
            }).collect(Collectors.toList());

            user.setChildren(children);
        }
        return user;
    }

}
