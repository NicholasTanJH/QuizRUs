package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public interface QuizPersistence {
    List<Quiz> getAllQuizzes();

    List<Quiz> getUserQuizzes(User user);

}
