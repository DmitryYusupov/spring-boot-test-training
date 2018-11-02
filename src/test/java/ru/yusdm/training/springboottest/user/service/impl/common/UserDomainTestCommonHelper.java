package ru.yusdm.training.springboottest.user.service.impl.common;

import ru.yusdm.training.springboottest.child.domain.Child;
import ru.yusdm.training.springboottest.passport.domain.Passport;
import ru.yusdm.training.springboottest.user.domain.User;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class UserDomainTestCommonHelper {

    private UserDomainTestCommonHelper() {

    }

    public static User createUserWithoutChildren(Long id) {
        User user = new User();
        user.setPassport(createUserPassport(user));
        user.setId(id);
        user.setName("UserName");
        return user;
    }

    public static void appendChildrenToUser(User user, int childNumber) {
        List<Child> children = IntStream.rangeClosed(1, childNumber).mapToObj(i -> new Child(user, "Child_" + i)).collect(toList());
        user.setChildren(children);
    }

    public static void appendChildrenToUserSettingId(User user, int childNumber, long childIdStart) {
        List<Child> children = IntStream.rangeClosed(1, childNumber).mapToObj(i -> new Child(childIdStart + i - 1, user, "Child_" + i)).collect(toList());
        user.setChildren(children);
    }


    public static Passport createUserPassport(User user) {
        return new Passport(null, user, "passportSerial");
    }

}
