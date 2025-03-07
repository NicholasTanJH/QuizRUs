package comp3350.quizrus.objects;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int userID;
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;

    public User(final int newID, final String newUsername, final String newPassword)
    {
        this.userID = newID;
        this.username = newUsername;
        this.password = newPassword;
        this.firstname = "";
        this.lastname = "";
    }

    public User(final String newUsername, final String newPassword, final String newFirstname, final String newLastname)
    {
        this.userID = -1;
        this.username = newUsername;
        this.password = newPassword;
        this.firstname = newFirstname;
        this.lastname = newLastname;
    }

    public User(final int newID, final String newUsername, final String newPassword, final String newFirstname, final String newLastname)
    {
        this.userID = newID;
        this.username = newUsername;
        this.password = newPassword;
        this.firstname = newFirstname;
        this.lastname = newLastname;
    }

    public int getUserID()
    {
        return this.userID;
    }

    public void setUserID(int newID){
        this.userID = newID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getFirstname()
    {
        return this.firstname;
    }

    public String getLastname()
    {
        return this.lastname;
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
