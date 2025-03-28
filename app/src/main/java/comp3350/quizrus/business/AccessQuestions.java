package comp3350.quizrus.business;

import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.persistence.QuestionPersistence;

public class AccessQuestions {
    private QuestionPersistence questionPersistence;
    private List<Question> questions;

    public AccessQuestions() {
        this.questionPersistence = Services.getQuestionPersistence();
        this.questions = null;
    }

    public AccessQuestions(final QuestionPersistence questionPersistence) {
        this();
        this.questionPersistence = questionPersistence;
    }

    public Question getQuestion(int questionID) {
        return questionPersistence.getQuestionByID(questionID);
    }

    public List<Question> getQuestions(final Quiz quiz) {
        questions = questionPersistence.getQuestionsForQuiz(quiz);
        return questions;
    }

    public Question createQuestion(final Quiz quiz, final String questionText, final String questionType) {
        int questionID = questionPersistence.insertQuestion(questionText, quiz, questionType);

        if (questionID != -1) {
            return new Question(questionID, questionText, quiz, questionType);
        } else {
            return null;
        }
    }
}
