package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private List<User> users;
    private int numUsers;

    public UserPersistenceStub() {
        this.users = new ArrayList<>();
        this.numUsers = 0;

        // Add users.
        insertUser("demo", "Password0!", "Jessie", "Andrade");
        insertUser("kakashi", "Password1!", "Saige", "Santana");
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(this.users);
    }

    @Override
    public User getUserByID(int userID) {
        for (User user : this.users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(final String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int insertUser(final String username, final String password, final String firstname, final String lastname) {
        User newUser = new User(numUsers, username, password, firstname, lastname);
        this.users.add(newUser);
        this.numUsers++;
        return newUser.getUserID();
    }
}
