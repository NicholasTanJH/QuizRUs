package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.User;

public interface UserPersistence {
    List<User> getAllUsers();

    User getUserByID(int userID);

    User getUserByUsername(String username);

    int insertUser(final User newUser);
}
