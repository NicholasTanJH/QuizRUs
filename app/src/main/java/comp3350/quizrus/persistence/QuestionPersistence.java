package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;

public interface QuestionPersistence {

    Question getQuestionByID(int questionID);

    List<Question> getQuestionsForQuiz(Quiz quiz);

    int insertQuestion(final String questionText, final Quiz quiz, final String questionType);
}
