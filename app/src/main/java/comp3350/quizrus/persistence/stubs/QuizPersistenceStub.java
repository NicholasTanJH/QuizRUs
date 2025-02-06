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

        User user1 = new User("kakashi", "password1");
        User user2 = new User("nazgul", "password1");

        quizzes.add(new Quiz("Flags of Countries", user1));
        quizzes.add(new Quiz("Celebrity Partners", user1));
        quizzes.add(new Quiz("Software Engineering I", user1));
        quizzes.add(new Quiz("Amazon Interview", user2));
        quizzes.add(new Quiz("Songs by Famous Artists", user2));
    }

    @Override
    public List<Quiz> getQuizzes() {
        return Collections.unmodifiableList(quizzes);
    }

    @Override
    public List<Quiz> getUserQuizzes(User user) {
        List<Quiz> userQuizzes = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            if (quiz.user.equals(user)) {
                userQuizzes.add(quiz);
            }
        }
        return Collections.unmodifiableList(userQuizzes);
    }
}
