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

    public AccessQuizzes() {
        quizPersistence = Services.getQuizPersistence();
        quizzes = null;
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

    /**
     * Search for a quiz with a specific quiz title based on a query string
     */
    public List<Quiz> searchQuizzes(String quizTitle) {
        if (quizTitle != null) {
            return quizPersistence.getQuizzesByTitle(quizTitle);
        } else {
            return null;
        }
    }

    public Quiz createQuiz(final User user, final String title, final int timer) {
        int quizID = quizPersistence.insertQuiz(title, user, timer);

        if (quizID != -1) {
            return new Quiz(quizID, title, user, timer);
        } else {
            return null;
        }
    }

    public boolean isQuizBelongsToUser(Quiz quiz, User user) {
        return quiz.getUser().getUserID() == user.getUserID();
    }

    public boolean deleteQuiz(Quiz quiz, User user) {
        if (isQuizBelongsToUser(quiz, user)) {
            quizPersistence.deleteQuiz(quiz);
            return true;
        } else {
            return false;
        }
    }
}
