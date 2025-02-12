package comp3350.quizrus.business;

import java.util.Collections;
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

//    public List<Question> getQuestions() {
//        questions = questionPersistence.getQuestionSequential();
//        return Collections.unmodifiableList(questions);
//    }

//    public Question getSequential() {
//        if (questions == null) {
//            questions = questionPersistence.getQuestionSequential();
//            currentQuestion = 0;
//        }
//        if (currentQuestion < questions.size()) {
//            question = questions.get(currentQuestion);
//            currentQuestion++;
//        } else {
//            questions = null;
//            question = null;
//            currentQuestion = 0;
//        }
//        return question;
//    }

//    public Question getRandom(int questionID) {
//        question = null;
//        if (questionID <= 0) {
//            // System.out.println("*** Invalid question ID");
//        } else {
//            questions = questionPersistence.getQuestionRandom(new Question(questionID));
//            if (questions.size() == 1) {
//                question = questions.get(0);
//            }
//        }
//        return question;
//    }
}
