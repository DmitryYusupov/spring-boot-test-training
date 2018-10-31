package ru.yusdm.training.springboottest.passport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PassportDto implements Serializable {
    private Long id;
    private String serialNumber;

}
