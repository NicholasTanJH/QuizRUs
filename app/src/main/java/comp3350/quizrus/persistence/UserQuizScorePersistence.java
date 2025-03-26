package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.Quiz;

public interface UserQuizScorePersistence {
    List<UserQuizScore> getScoresForQuiz(Quiz quiz, int numEntries);

    int getUserHighScore(Quiz quiz, User user);

    int getNumAttempts(Quiz quiz, User user);

    int insertScore(final User user, final Quiz quiz, final int numCorrect, final int timeTaken, final int score);
}
