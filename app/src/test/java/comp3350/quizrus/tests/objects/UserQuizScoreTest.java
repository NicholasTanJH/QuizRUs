package comp3350.quizrus.tests.objects;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;

public class UserQuizScoreTest {

    private User user;
    private Quiz quiz;
    private Timestamp timestamp;

    @Before
    public void setUp() {
        user = new User(1, "testUser", "password");
        quiz = new Quiz(1, "test", user);
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Test
    public void testConstructorWithID() {
        UserQuizScore score = new UserQuizScore(1, user, quiz, 5, 120, 80, timestamp);
        assertEquals(1, score.getUserQuizScoreID());
        assertEquals(user, score.getUser());
        assertEquals(quiz, score.getQuiz());
        assertEquals(5, score.getNumCorrect());
        assertEquals(120, score.getTimeTaken());
        assertEquals(80, score.getScore());
        assertEquals(timestamp, score.getTimeAdded());
    }

    @Test
    public void testConstructorWithoutID() {
        UserQuizScore score = new UserQuizScore(user, quiz, 3, 90, 60, timestamp);
        assertEquals(-1, score.getUserQuizScoreID()); // Default ID
        assertEquals(user, score.getUser());
        assertEquals(quiz, score.getQuiz());
        assertEquals(3, score.getNumCorrect());
        assertEquals(90, score.getTimeTaken());
        assertEquals(60, score.getScore());
        assertEquals(timestamp, score.getTimeAdded());
    }

    @Test
    public void testSetUserQuizScoreID() {
        UserQuizScore score = new UserQuizScore(user, quiz, 4, 100, 70, timestamp);
        assertEquals(-1, score.getUserQuizScoreID());
        score.setUserQuizScoreID(10);
        assertEquals(10, score.getUserQuizScoreID());
    }
}

