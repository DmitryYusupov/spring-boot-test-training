package ru.yusdm.training.springboottest.passport.dto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.common.solutions.Converter;
import ru.yusdm.training.springboottest.passport.domain.Passport;

@NoArgsConstructor
@Component
public class PassportFromDomainToDtoConverter implements Converter<Passport, PassportDto> {

    @Override
    public PassportDto convert(Passport passport) {
        return new PassportDto(passport.getId(), passport.getSerialNumber());
    }

}
