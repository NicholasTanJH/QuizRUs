package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private final int quizID;
    private final User myUser;
    private final String title;

    public Quiz(final int newQuizID, final String newTitle, final User newUser)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
    }

    public int getQuizID()
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

    @Override
    public String toString()
    {
        return String.format("Quiz: [quizID: %s, User: %s, Title: %s]", quizID, myUser != null ? myUser.getUserID() : "null", title);
    }

    @Override
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
