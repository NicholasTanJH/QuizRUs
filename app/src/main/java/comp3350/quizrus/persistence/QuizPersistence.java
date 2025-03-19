package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;

public interface QuizPersistence {
    List<Quiz> getAllQuizzes();

    Quiz getQuizByID(int quizID);

    List<Quiz> getUserQuizzes(User user);


    List<Quiz> getQuizzesByTitle(String quizTitle);

    int insertQuiz(Quiz quiz, User user);

    void deleteQuiz(Quiz quiz);
}
