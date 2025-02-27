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

    public User createUser(String username, final String password, final String email, final String firstname, final String lastname)
    {
        User newUser = new User(username.toLowerCase(), password, email, firstname, lastname);

//        int userID = userPersistence.createUser(newUser);
//        if(userID != -1)
//        {
//            newUser.setUserID(userID);
//        }
//        else
//        {
//            return null;
//        }

        return newUser;
    }

    public User getUser(final String username, final String password){
//        return userPersistence.getUser(username, password);
        return null;
    }

    public boolean authenticateUsername(String username)
    {
        return !username.isEmpty() && username.length() <= 20;
    }

    public boolean authenticatePassword(String password)
    {
        String upperCase = ".*[A-Z].*";
        String lowerCase = ".*[a-z].*";
        String numbers = ".*\\d.*";
        String specials = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";

        if(password.isEmpty()
        ||password.length() < 8
        || !password.matches(upperCase)
        || !password.matches(lowerCase)
        || !password.matches(numbers)
        || !password.matches(specials))
        {
            return false;
        }

        return true;
    }

    public boolean authenticateEmail(String email)
    {
        return !email.isEmpty();
    }

    public boolean authenticateName(String name)
    {
        return !name.isEmpty();
    }
}
