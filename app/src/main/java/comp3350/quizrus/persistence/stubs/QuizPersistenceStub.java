package comp3350.quizrus.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuizPersistence;

public class QuizPersistenceStub implements QuizPersistence {
    private List<Quiz> quizzes;
    private int numQuizzes;

    public QuizPersistenceStub() {
        this.quizzes = new ArrayList<>();

        this.numQuizzes = 0;

        // A quiz must be associated with a user.
        User user1 = new User(0, "demo", "Password0!", "demo@test.com", "Jessie", "Andrade");
        User user2 = new User(1, "kakashi", "Password1!", "kakashi@test.com", "Saige", "Santana");

        // Add quizzes.
        insertQuiz(new Quiz(0, "Flags of Countries", user1, 120), user1);
        insertQuiz(new Quiz(1, "Celebrity Partners", user2, 120), user2);
    }

    @Override
    public Quiz getQuizByID(int quizID) {
        for(Quiz quiz : quizzes)
        {
            if(quiz.getQuizID() == quizID)
            {
                return quiz;
            }
        }
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
        quiz.setQuizID(numQuizzes);
        quizzes.add(quiz);
        numQuizzes++;
        return quiz.getQuizID();
    }
}
