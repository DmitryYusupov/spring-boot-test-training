package ru.yusdm.training.springboottest.child.domain;

import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.common.solutions.Converter;

@Component
public class ChildFromDtoToDomainConverter implements Converter<ChildDto, Child> {

    @Override
    public Child convert(ChildDto childDto) {
        Child child = new Child();
        child.setId(childDto.getId());
        child.setName(childDto.getName());
        return child;
    }
}
