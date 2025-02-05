package comp3350.quizrus.objects;

import java.util.Objects;
public class User {

    private final String userID;
    private final String username;
    private final String password;

    public User(final String newID)
    {
        this.userID = newID;
        this.username = null;
        this.password = null;
    }

    public User(final String newID, final String newUsername, final String newPassword)
    {
        this.userID = newID;
        this.username = newUsername;
        this.password = newPassword;
    }

    public String getUserID()
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

    public String toString()
    {
        return String.format("User: %s %s %s", this.userID, this.username, this.password);
    }

    public int hashCode()
    {
        return Objects.hash(userID, username, password);
    }

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
