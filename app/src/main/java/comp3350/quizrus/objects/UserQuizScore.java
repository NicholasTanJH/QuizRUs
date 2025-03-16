package comp3350.quizrus.objects;

import java.sql.Timestamp;

import comp3350.quizrus.business.CalculateScore;

public class UserQuizScore {
    private int userQuizScoreID;
    private User user;
    private Quiz quiz;
    private int numCorrect;
    private int timeElapsed;
    private int score;
    private Timestamp timeAdded;

    public UserQuizScore(User newUser, Quiz newQuiz, int newNumCorrect, int newTimeElapsed, int newScore, Timestamp newTimeAdded)
    {
        this.userQuizScoreID = -1;
        this.user = newUser;
        this.quiz = newQuiz;
        this.numCorrect = newNumCorrect;
        this.timeElapsed = newTimeElapsed;
        this.score = newScore;
        this.timeAdded = newTimeAdded;
    }

    public UserQuizScore(int newUserQuizScoreID, User newUser, Quiz newQuiz, int newNumCorrect, int newTimeElapsed, int newScore, Timestamp newTimeAdded)
    {
        this.userQuizScoreID = newUserQuizScoreID;
        this.user = newUser;
        this.quiz = newQuiz;
        this.numCorrect = newNumCorrect;
        this.timeElapsed = newTimeElapsed;
        this.score = newScore;
        this.timeAdded = newTimeAdded;
    }

    public int getUserQuizScoreID() {
        return this.userQuizScoreID;
    }
    public void setUserQuizScoreID(final int newUserQuizScoreID)
    {
        this.userQuizScoreID = newUserQuizScoreID;
    }

    public User getUser() {
        return this.user;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public int getNumCorrect()
    {
        return this.numCorrect;
    }

    public int getTimeElapsed()
    {
        return this.timeElapsed;
    }

    public int getScore() {
        return this.score;
    }

    public Timestamp getTimeAdded() {
        return this.timeAdded;
    }
}
