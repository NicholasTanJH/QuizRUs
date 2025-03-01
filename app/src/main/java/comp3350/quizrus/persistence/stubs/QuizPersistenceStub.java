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

        User user1 = new User(1, "kakashi", "password1");
        User user2 = new User(2, "nazgul", "password1");

        quizzes.add(new Quiz(1, "Flags of Countries", user1));
        quizzes.add(new Quiz(2, "Celebrity Partners", user1));
    }

    @Override
    public Quiz getQuizByID(int quizID){
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
