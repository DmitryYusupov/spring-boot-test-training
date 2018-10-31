package ru.yusdm.training.springboottest.user.domain;

import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.passport.domain.PassportFromDtoToDomainConverter;
import ru.yusdm.training.springboottest.user.dto.UserDto;

public class UserFromDtoToDomainConverter implements Converter<UserDto, User> {

    public static final UserFromDtoToDomainConverter INSTANCE = new UserFromDtoToDomainConverter();

    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());

        if (userDto.getPassportDto() != null) {
            Passport passport = PassportFromDtoToDomainConverter.INSTANCE.convert(userDto.getPassportDto());
            passport.setUser(user);
            user.setPassport(passport);
        }

        if (userDto.getChildren() != null && !userDto.getChildren().isEmpty()) {

        }
        return user;
    }
}
