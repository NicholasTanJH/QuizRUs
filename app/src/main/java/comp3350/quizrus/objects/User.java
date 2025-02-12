package comp3350.quizrus.objects;

import java.util.Objects;
public class User {

    private final int userID;
    private final String username;
    private final String password;

    public User(final int newID, final String newUsername, final String newPassword)
    {
        this.userID = newID;
        this.username = newUsername;
        this.password = newPassword;
    }

    public int getUserID()
    {
        return this.userID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String toString()
    {
        return String.format("User: [UserID: %s, Username: %s, Password: %s]", this.userID, this.username, this.password);
    }

    @Override
    public boolean equals(Object other)
    {
        boolean equal = false;

        if(other instanceof User)
        {
            User otherUser = (User) other;
            equal = Objects.equals(this.userID, otherUser.userID);
        }

        return equal;
    }

}
