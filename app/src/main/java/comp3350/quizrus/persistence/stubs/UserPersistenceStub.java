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
        numUsers = 0;

        // Add users.
        users.add(new User(0, "demo", "Password0!", "Jessie", "Andrade"));
        users.add(new User(1, "kakashi", "Password1!", "Saige", "Santana"));
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public User getUserByID(int userID) {
        for(User user : users) {
            if (user.getUserID() == userID){
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        for(User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int insertUser(User newUser) {
        newUser.setUserID(numUsers);
        users.add(newUser);
        numUsers++;
        return newUser.getUserID();
    }
}
