package ru.yusdm.training.springboottest.passport.domain;

import lombok.*;
import ru.yusdm.training.springboottest.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PASSPORT")
public class Passport {

    @Id
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
}
