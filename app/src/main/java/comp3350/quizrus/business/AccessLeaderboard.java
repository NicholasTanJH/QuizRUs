package comp3350.quizrus.business;

import java.sql.Timestamp;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.UserQuizScorePersistence;

public class AccessLeaderboard {
    private UserQuizScorePersistence scorePersistence;
    private List<UserQuizScore> userQuizScores;

    public AccessLeaderboard() {
        this.scorePersistence = Services.getUserQuizScorePersistence();
        this.userQuizScores = null;
    }

    public AccessLeaderboard(final UserQuizScorePersistence scorePersistence) {
        this();
        this.scorePersistence = scorePersistence;
    }

    public List<UserQuizScore> getScoresForQuiz(Quiz quiz) {
        return scorePersistence.getScoresForQuiz(quiz);
    }

    public double getAverageScore(Quiz quiz, User user)
    {
        double avg = scorePersistence.getAverageScore(quiz, user);
        return Double.parseDouble(String.format("%.2f", avg));
    }

    public int getNumAttempts(Quiz quiz, User user)
    {
        return scorePersistence.getNumAttempts(quiz, user);
    }

    public UserQuizScore CreateUserQuizScore(final User user, final Quiz quiz, final int numCorrect,
            final int timeTaken, final int newScore, final Timestamp timeAdded) {
        UserQuizScore newUserQuizScore = new UserQuizScore(user, quiz, numCorrect, timeTaken, newScore, timeAdded);

        int userQuizScoreID = scorePersistence.insertScore(newUserQuizScore, user, quiz);
        if (userQuizScoreID != -1) {
            newUserQuizScore.setUserQuizScoreID(userQuizScoreID);
        } else {
            return null;
        }

        return newUserQuizScore;
    }

}
