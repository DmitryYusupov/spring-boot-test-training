package ru.yusdm.training.springboottest.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.yusdm.training.springboottest.passport.domain.Passport;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private Passport passport;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
