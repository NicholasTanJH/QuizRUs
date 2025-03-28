package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.PersistenceException;
import comp3350.quizrus.persistence.QuizPersistence;

public class QuizPersistenceStub implements QuizPersistence {
    private List<Quiz> quizzes;
    private int numQuizzes;

    public QuizPersistenceStub() {
        this.quizzes = new ArrayList<>();
        this.numQuizzes = 0;

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // Add quizzes.
        insertQuiz("Flags of Countries", user1, 120);
        insertQuiz("Celebrity Partners", user2, 10);
    }

    @Override
    public Quiz getQuizByID(int quizID) {
        for (Quiz quiz : this.quizzes) {
            if (quiz.getQuizID() == quizID) {
                return quiz;
            }
        }
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return Collections.unmodifiableList(this.quizzes);
    }

    @Override
    public List<Quiz> getQuizzesByTitle(String quizTitle) {
        List<Quiz> titledQuizzes = new ArrayList<>();

        for (Quiz quiz : this.quizzes) {
            if (quiz.getTitle().toLowerCase().contains(quizTitle.toLowerCase())) {
                titledQuizzes.add(quiz);
            }
        }

        return Collections.unmodifiableList(titledQuizzes);
    }

    @Override
    public int insertQuiz(final String title, final User user, final int timer) {
        Quiz newQuiz = new Quiz(numQuizzes, title, user, timer);
        this.quizzes.add(newQuiz);
        this.numQuizzes++;
        return newQuiz.getQuizID();
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        int index;

        index = this.quizzes.indexOf(quiz);
        if (index >= 0) {
            Quiz deletedQuiz = this.quizzes.remove(index);
            if (deletedQuiz == null)
                throw new PersistenceException(new Exception("Deleting quiz failed, no quizzes were affected."));
        }
    }
}
