package ru.yusdm.training.springboottest.passport.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.domain.Passport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportFromDomainToDtoConverter implements Converter<Passport, PassportDto> {

    public static final PassportFromDomainToDtoConverter INSTANCE = new PassportFromDomainToDtoConverter();

    @Override
    public PassportDto convert(Passport passport) {
        return new PassportDto(passport.getId(), passport.getSerialNumber());
    }

}
