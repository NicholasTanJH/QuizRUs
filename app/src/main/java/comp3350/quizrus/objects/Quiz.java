package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private final String quizID;
    private final String userID;
    private final String title;

    public Quiz(final String newID) {
        this.quizID = newID;
        this.userID = null;
        this.title = null;
    }

    public Quiz(final String newQuizID, final String newUserID, final String newTitle) {
        this.quizID = newQuizID;
        this.userID = newUserID;
        this.title = newTitle;
    }

    public String getQuizID() {
        return this.quizID;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String toString()
    {
        return String.format("Quiz: %s %s %s", quizID, userID, title);
    }

    public int hashCode()
    {
        return Objects.hash(quizID, userID, title);
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
