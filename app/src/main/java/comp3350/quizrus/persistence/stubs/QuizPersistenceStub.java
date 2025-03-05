package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuizPersistence;

public class QuizPersistenceStub implements QuizPersistence {
    private List<Quiz> quizzes;

    public QuizPersistenceStub() {
        this.quizzes = new ArrayList<>();

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "Saige", "Santana");

        // Add quizzes.
        quizzes.add(new Quiz(0, "Flags of Countries", user1, 120));
        quizzes.add(new Quiz(1, "Celebrity Partners", user2, 120));
    }

    @Override
    public Quiz getQuizByID(int quizID) {
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return Collections.unmodifiableList(quizzes);
    }

    @Override
    public List<Quiz> getUserQuizzes(User user) {
        List<Quiz> userQuizzes = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            if (quiz.getUser().equals(user)) {
                userQuizzes.add(quiz);
            }
        }
        return Collections.unmodifiableList(userQuizzes);
    }

    @Override
    public int insertQuiz(Quiz quiz, User user) {
        return -1;
    }
}
