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

        users.add(new User(1, "kakashi", "password1"));
        users.add(new User(2, "nazgul", "password2"));
        users.add(new User(3, "ringer", "password3"));
        users.add(new User(4, "sniper123", "password4"));
        users.add(new User(5, "eatgood", "password5"));
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }
}
