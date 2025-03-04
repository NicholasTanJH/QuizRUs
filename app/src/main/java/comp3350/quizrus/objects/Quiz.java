package comp3350.quizrus.objects;

import java.util.Objects;

public class Quiz {
    private int quizID;
    private final User myUser;
    private final String title;

    private final int timeLimit;

    public Quiz(final int newQuizID, final String newTitle, final User newUser)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
        this.timeLimit = 0;
    }

    public Quiz(final String newTitle, final User newUser, final int newtimeLimit)
    {
        this.quizID = -1;
        this.myUser = newUser;
        this.title = newTitle;
        this.timeLimit = newtimeLimit;
    }

    public Quiz(final int newQuizID, final String newTitle, final User newUser, final int newtimeLimit)
    {
        this.quizID = newQuizID;
        this.myUser = newUser;
        this.title = newTitle;
        this.timeLimit = newtimeLimit;
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
    
    public int getTimeLimit() {
        return this.timeLimit;
    }

    @Override
    public String toString()
    {
        return String.format("Quiz: [quizID: %s, User: %s, Title: %s, timeLimit: %s]", quizID, myUser != null ? myUser.getUserID() : "null", title, this.timeLimit);
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
