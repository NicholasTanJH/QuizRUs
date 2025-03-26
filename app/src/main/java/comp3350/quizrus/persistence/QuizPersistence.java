package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public interface QuizPersistence {
    List<Quiz> getAllQuizzes();

    Quiz getQuizByID(int quizID);

    List<Quiz> getQuizzesByTitle(String quizTitle);

    int insertQuiz(final String title, final User user, final int timer);

    void deleteQuiz(Quiz quiz);
}
