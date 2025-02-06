package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;

public interface QuestionPersistence {
    List<Question> getQuestionsForQuiz(Quiz quiz);

}
