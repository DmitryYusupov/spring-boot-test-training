package ru.yusdm.training.springboottest.child.dto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.common.solutions.Converter;

import javax.annotation.PostConstruct;

@NoArgsConstructor
@Component
public class ChildFromDomainToDtoConverter implements Converter<Child, ChildDto> {

    @Override
    public ChildDto convert(Child child) {
        return new ChildDto(child.getId(), child.getUser().getId(), child.getName());
    }
}
