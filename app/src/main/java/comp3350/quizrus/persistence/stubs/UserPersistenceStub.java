package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private List<User> users;

    public UserPersistenceStub() {
        this.users = new ArrayList<>();

        users.add(new User("kakashi", "password1"));
        users.add(new User("nazgul", "password2"));
        users.add(new User("ringer", "password3"));
        users.add(new User("sniper123", "password4"));
        users.add(new User("eatgood", "password5"));
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }
}
