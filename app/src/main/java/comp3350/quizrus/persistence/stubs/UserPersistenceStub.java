package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private List<User> users;
    private int numUsers;

    public UserPersistenceStub() {
        this.users = new ArrayList<>();

        // Add users.
        users.add(new User(0, "demo", "Password0!", "Jessie", "Andrade"));
        users.add(new User(1, "kakashi", "Password1!", "Saige", "Santana"));

        this.numUsers = 2;
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
    public User getUserByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int insertUser(User newUser) {
        // Check that the user isn't already in our list.
        for (User user : this.users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                throw new PersistenceException(
                        new Exception("A user with username " + newUser.getUsername() + " already exists."));
            }
        }

        // Add the user.
        newUser.setUserID(this.numUsers);
        this.users.add(newUser);
        this.numUsers++;
        return newUser.getUserID();
    }
}
