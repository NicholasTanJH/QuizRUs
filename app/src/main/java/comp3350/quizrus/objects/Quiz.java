package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private final String quizID;
    private final User myUser;
    private final String title;

    public Quiz(final String newID)
    {
        this.quizID = newID;
        this.myUser = null;
        this.title = null;
    }

    public Quiz(final String newQuizID, final String newTitle, final User newUser)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
    }

    public String getQuizID()
    {
        return this.quizID;
    }

    public User getUser()
    {
        return this.myUser;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String toString()
    {
        return String.format("Quiz: %s %s %s", quizID, myUser != null ? myUser.getUserID() : "null", title);
    }

    public int hashCode()
    {
        return Objects.hash(quizID, myUser, title);
    }

    public boolean equals(Object other)
    {
        boolean equal = false;

        if(other instanceof Quiz)
        {
            final Quiz otherQuiz = (Quiz) other;
            equal = Objects.equals(this.quizID, otherQuiz.quizID);
        }

        return equal;
    }

}
