package ru.yusdm.training.springboottest.passport.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.dto.PassportDto;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Component
public class PassportFromDtoToDomainConverter implements Converter<PassportDto, Passport> {

    @Override
    public Passport convert(PassportDto passportDto) {
        Passport passport = new Passport();
        passport.setSerialNumber(passportDto.getSerialNumber());
        passport.setId(passportDto.getId());
        return passport;
    }
}
