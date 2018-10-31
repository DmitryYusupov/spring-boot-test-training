package ru.yusdm.training.springboottest.child.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChildDto implements Serializable {
    private Long id;
    private Long userId;
    private String name;

}
