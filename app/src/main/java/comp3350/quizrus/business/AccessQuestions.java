package comp3350.quizrus.business;

import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.QuestionPersistence;

import comp3350.quizrus.objects.Quiz;
public class AccessQuestions {
    private QuestionPersistence questionPersistence;
    private List<Question> questions;
    private Question question;
    private int currentQuestion;

    public AccessQuestions() {
        this.questionPersistence = Services.getQuestionPersistence();
        this.questions = null;
        this.question = null;
        this.currentQuestion = 0;
    }

    public AccessQuestions(final QuestionPersistence questionPersistence) {
        this();
        this.questionPersistence = questionPersistence;
    }

    public List<Question> getQuestions(final Quiz quiz) {
        questions = questionPersistence.getQuestionsForQuiz(quiz);
        return questions;
    }
}
