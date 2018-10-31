package ru.yusdm.training.springboottest.passport.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.dto.PassportDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportFromDtoToDomainConverter implements Converter<PassportDto, Passport> {

    public static final PassportFromDtoToDomainConverter INSTANCE = new PassportFromDtoToDomainConverter();

    @Override
    public Passport convert(PassportDto passportDto) {
        Passport passport = new Passport();
        passport.setSerialNumber(passportDto.getSerialNumber());
        passport.setId(passportDto.getId());
        return passport;
    }
}
