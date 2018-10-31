package ru.yusdm.training.springboottest.user.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.passport.domain.Passport;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(optional = false, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, mappedBy = "user")
    /**
     * If we don't use dto and sent domain to avoid recursion
     */
    @JsonManagedReference
    private Passport passport;

    @OneToMany(targetEntity = Child.class,
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY)
    /**
     * If we don't use dto and sent domain to avoid recursion
     */
    @JsonManagedReference
    private List<Child> children;

}
