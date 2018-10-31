package ru.yusdm.training.springboottest.child.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.common.solutions.Converter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChildFromDomainToDtoConverter implements Converter<Child, ChildDto> {

    public static final ChildFromDomainToDtoConverter INSTANCE = new ChildFromDomainToDtoConverter();

    @Override
    public ChildDto convert(Child child) {
        return new ChildDto(child.getId(), child.getUser().getId(), child.getName());
    }
}
