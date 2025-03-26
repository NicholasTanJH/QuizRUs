package comp3350.quizrus.tests.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


import comp3350.quizrus.business.AccessLeaderboard;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.UserQuizScorePersistence;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessLeaderboardTest {
    private AccessLeaderboard accessLeaderboard;
    private File tempDB;

    @Mock
    private UserQuizScorePersistence mockScorePersistence;

    @Before
    public void setUp() throws IOException {
        mockScorePersistence = mock(UserQuizScorePersistence.class);
        this.tempDB = TestUtils.copyDB();
        accessLeaderboard = new AccessLeaderboard(mockScorePersistence);
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

    @Test
    public void testGetScoresForQuiz() {
        User user1 = new User(1, "user1", "password");
        User user2 = new User(2, "user2", "password");
        Quiz quiz = new Quiz(1, "test", user1);

        UserQuizScore score1 = new UserQuizScore(user1, quiz, 5, 30, 500, Timestamp.valueOf("2025-03-20 12:00:00"));
        UserQuizScore score2 = new UserQuizScore(user2, quiz, 3, 40, 300, Timestamp.valueOf("2025-03-20 12:05:00"));

        List<UserQuizScore> mockScores = Arrays.asList(score1, score2);

        when(mockScorePersistence.getScoresForQuiz(quiz, 5)).thenReturn(mockScores);

        List<UserQuizScore> retrievedScores = accessLeaderboard.getScoresForQuiz(quiz, 5);
        assertNotNull(retrievedScores);
        assertEquals(2, retrievedScores.size());
        assertEquals(500, retrievedScores.get(0).getScore());
        assertEquals(300, retrievedScores.get(1).getScore());
    }

    @Test
    public void testGetUserHighScore() {
        User user = new User(1, "testUser", "password");
        Quiz quiz = new Quiz(1, "test", user);

        when(mockScorePersistence.getUserHighScore(quiz, user)).thenReturn(150);

        int highScore = accessLeaderboard.getUserHighScore(quiz, user);
        assertEquals(150, highScore);
    }

    @Test
    public void testGetNumAttempts() {
        User user = new User(1, "testUser", "password");
        Quiz quiz = new Quiz(1, "test", user);

        when(mockScorePersistence.getNumAttempts(quiz, user)).thenReturn(3);

        int numAttempts = accessLeaderboard.getNumAttempts(quiz, user);
        assertEquals(3, numAttempts);
    }

    @Test
    public void testCreateUserQuizScore() {
        User user = new User(1, "testUser", "password");
        Quiz quiz = new Quiz(1, "test", user);
        Timestamp timeAdded = Timestamp.valueOf("2025-03-20 12:00:00");

        UserQuizScore mockScore = new UserQuizScore(user, quiz, 4, 30, 400, timeAdded);

        // Mock behavior: insertScore should return a valid userQuizScoreID
        when(mockScorePersistence.insertScore(any(UserQuizScore.class), eq(user), eq(quiz))).thenReturn(10);

        UserQuizScore createdScore = accessLeaderboard.CreateUserQuizScore(user, quiz, 4, 30, 400, timeAdded);

        assertNotNull(createdScore);
        assertEquals(10, createdScore.getUserQuizScoreID()); // Ensure the returned object has correct ID
        assertEquals(4, createdScore.getNumCorrect());
        assertEquals(30, createdScore.getTimeTaken());
        assertEquals(400, createdScore.getScore());
        assertEquals(timeAdded, createdScore.getTimeAdded());
    }

    @Test
    public void testCreateUserQuizScore_Failure() {
        User user = new User(1, "testUser", "password");
        Quiz quiz = new Quiz(1, "test", user);
        Timestamp timeAdded = Timestamp.valueOf("2025-03-20 12:00:00");

        // Mock behavior: insertScore fails (returns -1)
        when(mockScorePersistence.insertScore(any(UserQuizScore.class), eq(user), eq(quiz))).thenReturn(-1);

        UserQuizScore createdScore = accessLeaderboard.CreateUserQuizScore(user, quiz, 4, 30, 400, timeAdded);

        assertNull(createdScore); // Should return null if score insertion fails
    }
}

