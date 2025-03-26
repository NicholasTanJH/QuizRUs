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

    public List<UserQuizScore> getScoresForQuiz(Quiz quiz, int numEntries) {
        return scorePersistence.getScoresForQuiz(quiz, numEntries);
    }

    public int getUserHighScore(Quiz quiz, User user)
    {
        return scorePersistence.getUserHighScore(quiz, user);
    }

    public int getNumAttempts(Quiz quiz, User user)
    {
        return scorePersistence.getNumAttempts(quiz, user);
    }

    public UserQuizScore CreateUserQuizScore(final User user, final Quiz quiz, final int numCorrect, final int timeTaken, final int score, final Timestamp timeAdded) {
        int userQuizScoreID = scorePersistence.insertScore(user, quiz, numCorrect, timeTaken, score, timeAdded);

        if (userQuizScoreID != -1) {
            return new UserQuizScore(userQuizScoreID, user, quiz, numCorrect, timeTaken, score, timeAdded);
        } else {
            return null;
        }
    }

}
