package ru.yusdm.training.springboottest.passport.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Passport {
    private Long id;
    private Long userId;
    private String serialNumber;
}
