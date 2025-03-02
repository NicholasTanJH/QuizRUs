package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;


public class AccessUsers {
    private UserPersistence userPersistence;
    private List<User> users;
    private User user;
    private int currentUser;

    public AccessUsers() {
        this.userPersistence = Services.getUserPersistence();
        this.users = null;
        this.user = null;
        this.currentUser = 0;
    }

    public AccessUsers(final UserPersistence userPersistence) {
        this();
        this.userPersistence = userPersistence;
    }

    public User getUser(int userID) {
        return userPersistence.getUserByID(userID);
    }

    public List<User> getUsers() {
        users = userPersistence.getAllUsers();
        return Collections.unmodifiableList(users);
    }

    public User createUser(String username, final String password, final String email, final String firstname, final String lastname) {
        User newUser = new User(username.toLowerCase(), password, email, firstname, lastname);

        int userID = userPersistence.insertUser(newUser);
        if(userID != -1)
        {
            newUser.setUserID(userID);
        }
        else
        {
            return null;
        }

        return newUser;
    }

    public User loginUser(final String username, final String password) {
        User user = userPersistence.getUserByUsername(username);
        if(user != null && password.equals(user.getPassword()))
        {
            return user;
        }
        return null;
    }

    public String authenticateUsername(String username) {

        String errorMessage = "";
        User user = userPersistence.getUserByUsername(username);
        boolean found = (user != null);
        if(found)
        {
            errorMessage += "\n \t - Username is taken";
        }
        if(username.isEmpty() || username.length() > 20)
        {
            errorMessage += "\n \t - 20 characters or shorter";
        }
        return errorMessage;
    }

    public String authenticatePassword(String password) {
        String upperCase = ".*[A-Z].*";
        String lowerCase = ".*[a-z].*";
        String numbers = ".*\\d.*";
        String specials = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";

        String errorMessage = "";
        if (password.length() < 8) {
            errorMessage += "\n \t - 8 or more characters";
        }
        if (!password.matches(upperCase)) {
            errorMessage += "\n \t - Upper case (A-Z)";
        }
        if (!password.matches(lowerCase)) {
            errorMessage += "\n \t - Smaller case (a-z)";
        }
        if (!password.matches(numbers)) {
            errorMessage += "\n \t - Number (0-9)";
        }
        if (!password.matches(specials)) {
            errorMessage += "\n \t - Special character (eg. !@#$%^&*()_+)";
        }

        return errorMessage;
    }

    public boolean authenticateEmail(String email) {
        String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        return !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean authenticateName(String name) {
        return !name.isEmpty();
    }
}
