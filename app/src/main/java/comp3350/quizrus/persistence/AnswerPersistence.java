package comp3350.quizrus.persistence;

import java.util.List;

import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;

public interface AnswerPersistence {
    List<Answer> getAnswersForQuestions(Question question);

    int insertAnswer(Answer answer, Question question);
}
