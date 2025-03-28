package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;

public class AccessUsers {
    private UserPersistence userPersistence;
    private List<User> users;

    public AccessUsers() {
        this.userPersistence = Services.getUserPersistence();
        this.users = null;
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

    public User createUser(String username, final String password, final String firstname, final String lastname) {
        int userID = userPersistence.insertUser(username.toLowerCase(), password, firstname, lastname);

        if (userID != -1) {
            return new User(userID, username.toLowerCase(), password, firstname, lastname);
        } else {
            return null;
        }
    }

    /**
     * Checks if a user with the inputted username exists and confirms password
     */
    public User loginUser(final String username, final String password) {
        User user = userPersistence.getUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * when creating a new account, checks that each inputted field follows the
     * requirements
     */
    public String authenticateUser(String newUsername, String newPassword, String newConfirmedPassword,
            String newFirstname, String newLastname) {
        String errorMessage = "";

        // we only want to show an error for the first inputted field that is incorrect
        if (errorMessage.equals("")) {
            errorMessage = authenticateUsername(newUsername);
        }

        if (errorMessage.equals("")) {
            errorMessage = authenticatePassword(newPassword);
        }

        if (errorMessage.equals("")) {
            if (!newPassword.equals(newConfirmedPassword)) {
                errorMessage = "Please ensure the confirmed password matches your password.";
            }
        }

        if (errorMessage.equals("")) {
            if (!authenticateName(newFirstname)) {
                errorMessage = "Please fill in your first name.";
            }
        }

        if (errorMessage.equals("")) {
            if (!authenticateName(newLastname)) {
                errorMessage = "Please fill in your last name.";
            }
        }

        return errorMessage;
    }

    /**
     * checks if username has not been taken and is of correct length
     */
    public String authenticateUsername(String username) {
        username = username.trim();
        String errorMessage = "";

        User user = userPersistence.getUserByUsername(username);

        boolean found = (user != null);
        if (found) {
            errorMessage += "\n \t - Username is taken";
        }
        if (username.isEmpty() || username.length() > 20) {
            errorMessage += "\n \t - 20 characters or shorter";
        }

        if (errorMessage.isEmpty()) {
            return errorMessage;
        } else {
            return "Username must be:\n" + errorMessage;
        }
    }

    /**
     * checks if password has all of the following: 8 or more characters, lowercase,
     * uppercase, number, and a special character
     */
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
            errorMessage += "\n \t - Lower case (a-z)";
        }
        if (!password.matches(numbers)) {
            errorMessage += "\n \t - Number (0-9)";
        }
        if (!password.matches(specials)) {
            errorMessage += "\n \t - Special character (eg. !@#$%^&*()_+)";
        }

        if (errorMessage.isEmpty()) {
            return errorMessage;
        } else {
            return "Password must have:\n" + errorMessage;
        }
    }

    public boolean authenticateName(String name) {
        return !name.isEmpty();
    }
}
