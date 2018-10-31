package ru.yusdm.training.springboottest.child.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.yusdm.training.springboottest.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CHILD")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    /**
     * If we don't use dto and sent domain to avoid recursion
     */
    @JsonBackReference
    private User user;

    @Column(name = "NAME")
    private String name;

    public Child(User user, String name) {
        this.user = user;
        this.name = name;
    }
}
