package comp3350.quizrus.tests.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;

public class UserQuizScoreTest {
    private User user;
    private Quiz quiz;

    @Before
    public void setUp() {
        user = new User(1, "testUser", "password", "test", "test");
        quiz = new Quiz(1, "test", user, 10);
    }

    @Test
    public void testConstructorWithID() {
        UserQuizScore score = new UserQuizScore(1, user, quiz, 5, 120, 80);
        assertEquals(1, score.getUserQuizScoreID());
        assertEquals(user, score.getUser());
        assertEquals(quiz, score.getQuiz());
        assertEquals(5, score.getNumCorrect());
        assertEquals(120, score.getTimeTaken());
        assertEquals(80, score.getScore());
    }
}
