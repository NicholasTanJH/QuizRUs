package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.User;

public interface UserPersistence {
    List<User> getAllUsers();

    User getUserByID(int userID);

    User getUserByUsername(final String username);

    int insertUser(final String username, final String password, final String firstname, final String lastname);
}
