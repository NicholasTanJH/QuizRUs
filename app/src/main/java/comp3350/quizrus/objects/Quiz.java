package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private final String quizID;
    private final String title;

    public Quiz(final String newID)
    {
        this.quizID = newID;
        this.title = null;
    }

    public Quiz(final String newID, final String newTitle)
    {
        this.quizID = newID;
        this.title = newTitle;
    }

    public String getQuizID()
    {
        return this.quizID;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String toString()
    {
        return String.format("Quiz: %s %s", quizID, title);
    }

    public int hashCode()
    {
        return Objects.hash(quizID, title);
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
