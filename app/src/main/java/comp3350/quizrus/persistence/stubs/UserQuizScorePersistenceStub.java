package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.UserQuizScorePersistence;

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
    public int insertScore(UserQuizScore userQuizScore, User user, Quiz Quiz) {
        userQuizScore.setUserQuizScoreID(this.numScores);
        this.scores.add(userQuizScore);
        this.numScores++;
        return userQuizScore.getUserQuizScoreID();
    }
}
