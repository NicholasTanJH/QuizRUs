package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;

public class AccessUsers {
    private UserPersistence userPersistence;
    private List<User> users;
    private User user;
    private int currentUser;


    public AccessUsers()
    {
        this.userPersistence = Services.getUserPersistence();
        this.users = null;
        this.user = null;
        this.currentUser = 0;
    }

    public AccessUsers(final UserPersistence userPersistence)
    {
        this();
        this.userPersistence = userPersistence;
    }

    public List<User> getUsers()
    {
        users = userPersistence.getAllUsers();
        return Collections.unmodifiableList(users);
    }
}
