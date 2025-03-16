package comp3350.quizrus.objects;

import java.sql.Timestamp;

public class UserQuizScore {
    private int userQuizScoreID;
    private int userID;
    private int quizID;
    private int score;
    private Timestamp timeAdded;

    public UserQuizScore(int userQuizScoreID, int userID, int quizID, int score, Timestamp timeAdded) {
        this.userQuizScoreID = userQuizScoreID;
        this.userID = userID;
        this.quizID = quizID;
        this.score = score;
        this.timeAdded = timeAdded;
    }

    public int getUserQuizScoreID() {
        return this.userQuizScoreID;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getQuizID() {
        return this.quizID;
    }

    public int getScore() {
        return this.score;
    }

    public Timestamp getTimeAdded() {
        return this.timeAdded;
    }
}
