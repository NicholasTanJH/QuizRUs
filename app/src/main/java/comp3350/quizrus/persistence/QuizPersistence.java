package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public interface QuizPersistence {
    List<Quiz> getAllQuizzes();

    Quiz getQuizByID(int quizID);

    int insertQuiz(Quiz quiz, User user);
}
