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

    public List<UserQuizScore> getScoresForUser(User user)
    {
        return scorePersistence.getScoresForUser(user);
    }

    public List<UserQuizScore> getScoresForQuiz(Quiz quiz)
    {
        return scorePersistence.getScoresForQuiz(quiz);
    }

    public UserQuizScore CreateUserQuizScore(final User user, final Quiz quiz, final int numCorrect, final int timeElapsed, final int newScore, final Timestamp timeAdded)
    {
        UserQuizScore newUserQuizScore = new UserQuizScore(user, quiz, numCorrect, timeElapsed, newScore, timeAdded);

        int userQuizScoreID = scorePersistence.insertScore(newUserQuizScore, user, quiz);
        if(userQuizScoreID != -1)
        {
            newUserQuizScore.setUserQuizScoreID(userQuizScoreID);
        }
        else
        {
            return null;
        }

        return newUserQuizScore;
    }

}


