package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private int quizID;
    private final User myUser;
    private final String title;

    private final long timer;

    public Quiz(final int newQuizID, final String newTitle, final User newUser)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
        this.timer = 0;
    }

    public Quiz(final String newTitle, final User newUser, final long newTimer)
    {
        this.quizID = -1;
        this.myUser = newUser;
        this.title = newTitle;
        this.timer = newTimer;
    }

    public Quiz(final int newQuizID, final String newTitle, final User newUser, final long newTimer)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
        this.timer = newTimer;
    }

    public int getQuizID()
    {
        return this.quizID;
    }

    public void setQuizID(int newID){
        this.quizID = newID;
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
        return String.format("Quiz: [quizID: %s, User: %s, Title: %s, Timer: %s]", quizID, myUser != null ? myUser.getUserID() : "null", title, this.timer);
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
