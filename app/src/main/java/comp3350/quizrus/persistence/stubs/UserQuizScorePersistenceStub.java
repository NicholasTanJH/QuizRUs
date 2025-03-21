package comp3350.quizrus.persistence.stubs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.UserQuizScorePersistence;
import comp3350.quizrus.persistence.hsqldb.DatabaseManager;

public class UserQuizScorePersistenceStub implements UserQuizScorePersistence {
    private List<UserQuizScore> scores;
    private int numScores;

    public UserQuizScorePersistenceStub() {
        this.scores = new ArrayList<>();
        this.numScores = 0;

        /****************************************
         * This is such a fun part to hardcode
         * the entries and double check if they're
         * correct that I'll leave it to the next
         * person to do it :)
         ****************************************/
    }

    @Override
    public List<UserQuizScore> getScoresForQuiz(Quiz quiz) {
        List<UserQuizScore> quizScores = new ArrayList<>();

        for (UserQuizScore score : this.scores) {
            if (score.getQuiz().getQuizID() == quiz.getQuizID()) {
                quizScores.add(score);
            }
        }

        return Collections.unmodifiableList(quizScores);
    }

    @Override
    public int getUserHighScore(Quiz quiz, User user)
    {
        int highestScore = 0;

        for(UserQuizScore score : this.scores)
        {
            if(score.getQuiz().getQuizID() == quiz.getQuizID() && score.getUser().getUserID() == user.getUserID())
            {
                if(score.getScore() > highestScore)
                {
                    highestScore = score.getScore();
                }
            }
        }
        return highestScore;
    }

    @Override
    public double getAverageScore(Quiz quiz, User user)
    {
        double totalScore = 0;
        int numAttempts = 0;

        for (UserQuizScore score : this.scores)
        {
            if(score.getQuiz().getQuizID() == quiz.getQuizID() && score.getUser().getUserID() == user.getUserID())
            {
                totalScore += score.getScore();
                numAttempts++;
            }
        }

        if(numAttempts == 0)
        {
            return 0;
        }
        else
        {
            return totalScore / numAttempts;
        }
    }

    @Override
    public int getNumAttempts(Quiz quiz, User user)
    {
        int numAttempts = 0;

        for (UserQuizScore score : this.scores)
        {
            if(score.getQuiz().getQuizID() == quiz.getQuizID() && score.getUser().getUserID() == user.getUserID())
            {
                numAttempts++;
            }
        }

        return numAttempts;
    }

    @Override
    public int insertScore(final User user, final Quiz quiz, final int numCorrect, final int timeTaken, final int score, final Timestamp timeAdded) {
        UserQuizScore newUserQuizScore = new UserQuizScore(numScores, user, quiz, numCorrect, timeTaken, score, timeAdded);
        this.scores.add(newUserQuizScore);
        this.numScores++;
        return newUserQuizScore.getUserQuizScoreID();
    }
}
