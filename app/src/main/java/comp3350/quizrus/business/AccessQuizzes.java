package comp3350.quizrus.business;

import java.util.Collections;
import java.util.List;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.QuizPersistence;

public class AccessQuizzes {
    private QuizPersistence quizPersistence;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private int currentQuiz;

    public AccessQuizzes() {
        quizPersistence = Services.getQuizPersistence();
        quizzes = null;
        quiz = null;
        currentQuiz = 0;
    }

    public AccessQuizzes(final QuizPersistence quizPersistence) {
        this();
        this.quizPersistence = quizPersistence;
    }

    public Quiz getQuiz(int quizID) {
        return quizPersistence.getQuizByID(quizID);
    }

    public List<Quiz> getQuizzes() {
        quizzes = quizPersistence.getAllQuizzes();
        return Collections.unmodifiableList(quizzes);
    }

    public Quiz createQuiz(final User user, final String title, final int timer) {
        Quiz newQuiz = new Quiz(title, user, timer);

        int quizID = quizPersistence.insertQuiz(newQuiz, user);
        if (quizID != -1) {
            newQuiz.setQuizID(quizID);
        } else {
            return null;
        }

        return newQuiz;
    }

    public boolean deleteQuiz(Quiz quiz, User user) {
        if (quiz.getUser().getUserID() == user.getUserID()) {
            quizPersistence.deleteQuiz(quiz);
            return true;
        } else {
            return false;
        }
    }
}
