package comp3350.quizrus.business;

import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.Quiz;
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

    public Question getQuestion(int questionID) {
        return questionPersistence.getQuestionByID(questionID);
    }

    public List<Question> getQuestions(final Quiz quiz) {
        questions = questionPersistence.getQuestionsForQuiz(quiz);
        return questions;
    }

    public Question createQuestion(final Quiz quiz, final String questionText, final String questionType)
    {
        Question newQuestion = new Question(questionText, quiz, questionType);

        int questionID = questionPersistence.insertQuestion(newQuestion, quiz);
        if(questionID != -1)
        {
            newQuestion.setQuestionID(questionID);
        }
        else
        {
            return null;
        }

        return newQuestion;
    }
}
