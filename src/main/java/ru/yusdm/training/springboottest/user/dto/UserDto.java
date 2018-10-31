package ru.yusdm.training.springboottest.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yusdm.training.springboottest.child.dto.ChildDto;
import ru.yusdm.training.springboottest.passport.dto.PassportDto;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;
    private String name;

    private PassportDto passportDto;
    private List<ChildDto> children;

}
