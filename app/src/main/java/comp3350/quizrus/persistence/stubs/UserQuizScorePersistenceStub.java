package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.UserQuizScorePersistence;

public class UserQuizScorePersistenceStub implements UserQuizScorePersistence {
    private List<UserQuizScore> userQuizScores;
    private int numScores;

    public UserQuizScorePersistenceStub() {
        this.userQuizScores = new ArrayList<>();
        this.numScores = 0;

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // Create a quiz.
        Quiz quiz1 = new Quiz(0, "Flags of Countries", user1, 120);

        // Add a few scores for the quiz.
        insertScore(user1, quiz1, 2, 17, 371);
        insertScore(user1, quiz1, 1, 42, 165);
        insertScore(user2, quiz1, 1, 69, 142);
    }

    @Override
    public List<UserQuizScore> getScoresForQuiz(Quiz quiz, int numEntries) {
        List<UserQuizScore> quizScores = new ArrayList<>();
        int currNumEntries = 0;

        for (UserQuizScore userQuizScore : this.userQuizScores) {
            if (userQuizScore.getQuiz().getQuizID() == quiz.getQuizID()) {
                quizScores.add(userQuizScore);
                currNumEntries++;
                if (currNumEntries >= numEntries) {
                    break;
                }
            }
        }

        // Order the quiz scores by the score.
        quizScores.sort((score1, score2) -> Integer.compare(score2.getScore(), score1.getScore()));

        return Collections.unmodifiableList(quizScores);
    }

    @Override
    public int getUserHighScore(Quiz quiz, User user) {
        int highestScore = 0;

        for (UserQuizScore userQuizScore : this.userQuizScores) {
            if (userQuizScore.getQuiz().getQuizID() == quiz.getQuizID() && userQuizScore.getUser().getUserID() == user.getUserID()) {
                if (userQuizScore.getScore() > highestScore) {
                    highestScore = userQuizScore.getScore();
                }
            }
        }
        return highestScore;
    }

    @Override
    public int getNumAttempts(Quiz quiz, User user) {
        int numAttempts = 0;

        for (UserQuizScore userQuizScore : this.userQuizScores) {
            if (userQuizScore.getQuiz().getQuizID() == quiz.getQuizID() && userQuizScore.getUser().getUserID() == user.getUserID()) {
                numAttempts++;
            }
        }

        return numAttempts;
    }

    @Override
    public int insertScore(final User user, final Quiz quiz, final int numCorrect, final int timeTaken,
            final int score) {
        UserQuizScore newUserQuizScore = new UserQuizScore(numScores, user, quiz, numCorrect, timeTaken, score);
        this.userQuizScores.add(newUserQuizScore);
        this.numScores++;
        return newUserQuizScore.getUserQuizScoreID();
    }
}
