package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

import java.io.File;
import java.io.IOException;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.business.AccessLeaderboard;
import comp3350.quizrus.business.CalculateScore;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.UserQuizScore;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.UserQuizScorePersistence;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.persistence.stubs.UserQuizScorePersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessLeaderboardIT {
    private File tempDB;
    private AccessUsers accessUsers;
    private AccessQuizzes accessQuizzes;
    private AccessLeaderboard accessLeaderboard;

    @Before
    public void setUp() throws IOException {
        // Load the database.
        this.tempDB = TestUtils.copyDB();

        // Reset the user persistence object if we are using a stub.
        UserPersistence userPersistence = Services.getUserPersistence();
        if (userPersistence instanceof UserPersistenceStub) {
            userPersistence = new UserPersistenceStub();
        }
        accessUsers = new AccessUsers(userPersistence);

        // Reset the quiz persistence object if we are using a stub.
        QuizPersistence quizPersistence = Services.getQuizPersistence();
        if (quizPersistence instanceof QuizPersistenceStub) {
            quizPersistence = new QuizPersistenceStub();
        }
        accessQuizzes = new AccessQuizzes(quizPersistence);

        // Reset the user quiz score persistence object if we are using a stub.
        UserQuizScorePersistence userQuizScorePersistence = Services.getUserQuizScorePersistence();
        if (userQuizScorePersistence instanceof UserQuizScorePersistenceStub) {
            userQuizScorePersistence = new UserQuizScorePersistenceStub();
        }
        this.accessLeaderboard = new AccessLeaderboard(userQuizScorePersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testGetScoresForQuiz() {
        Quiz quiz1 = accessQuizzes.getQuiz(0);

        // Get the user quiz scores.
        List<UserQuizScore> userQuizScores = accessLeaderboard.getScoresForQuiz(quiz1, 5);
        assertEquals(3, userQuizScores.size());

        // Check that the values for the first user score record are correct.
        assertEquals(0, userQuizScores.getFirst().getUserQuizScoreID());
        assertNotNull(userQuizScores.getFirst().getUser());
        assertEquals(0, userQuizScores.getFirst().getUser().getUserID());
        assertNotNull(userQuizScores.getFirst().getQuiz());
        assertEquals(0, userQuizScores.getFirst().getQuiz().getQuizID());
        assertEquals(2, userQuizScores.getFirst().getNumCorrect());
        assertEquals(17, userQuizScores.getFirst().getTimeTaken());
        assertEquals(371, userQuizScores.getFirst().getScore());

        // Check that the values for the last user score record are correct.
        assertEquals(2, userQuizScores.getLast().getUserQuizScoreID());
        assertNotNull(userQuizScores.getLast().getUser());
        assertEquals(1, userQuizScores.getLast().getUser().getUserID());
        assertNotNull(userQuizScores.getLast().getQuiz());
        assertEquals(0, userQuizScores.getLast().getQuiz().getQuizID());
        assertEquals(1, userQuizScores.getLast().getNumCorrect());
        assertEquals(69, userQuizScores.getLast().getTimeTaken());
        assertEquals(142, userQuizScores.getLast().getScore());
    }

    @Test
    public void testGetHighScore() {
        // Get a user and a quiz.
        User user1 = accessUsers.getUser(0);
        Quiz quiz1 = accessQuizzes.getQuiz(0);

        // Get the highest score of the user for the given quiz.
        int highScore1 = accessLeaderboard.getUserHighScore(quiz1, user1);
        assertEquals(371, highScore1);

        // Insert a new highest score for the user.
        int score = CalculateScore.calculateScore(5, 120, 10);
        accessLeaderboard.CreateUserQuizScore(user1, quiz1, 5, 5, score);

        // Check the new highest score.
        int highScore2 = accessLeaderboard.getUserHighScore(quiz1, user1);
        assertEquals(score, highScore2);
        assertEquals(958, highScore2);
    }

    @Test
    public void testGetNumAttempts() {
        // Get a user and a quiz.
        User user1 = accessUsers.getUser(0);
        Quiz quiz1 = accessQuizzes.getQuiz(0);

        // Verify that we have the correct number of attempts
        int numAttempts1 = accessLeaderboard.getNumAttempts(quiz1, user1);
        assertEquals(2, numAttempts1);

        // Insert a new quiz score.
        int score = CalculateScore.calculateScore(2, 120, 10);
        accessLeaderboard.CreateUserQuizScore(user1, quiz1, 5, 5, score);

        // Check the new number of attempts.
        int numAttempts2 = accessLeaderboard.getNumAttempts(quiz1, user1);
        assertEquals(3, numAttempts2);
    }

    @Test
    public void testCreateUserQuizScore() {
        // Get a user and a quiz.
        User user1 = accessUsers.getUser(0);
        Quiz quiz1 = accessQuizzes.getQuiz(0);

        // Check the number of scores before insert.
        List<UserQuizScore> userQuizScores1 = accessLeaderboard.getScoresForQuiz(quiz1, 5);
        assertEquals(3, userQuizScores1.size());

        // Insert a new quiz score.
        int score = CalculateScore.calculateScore(1, 120, 119);
        UserQuizScore userQuizScore = accessLeaderboard.CreateUserQuizScore(user1, quiz1, 1, 119, score);
        assertNotNull(userQuizScore);

        // Check the number of scores after insert.
        List<UserQuizScore> userQuizScores2 = accessLeaderboard.getScoresForQuiz(quiz1, 5);
        assertEquals(4, userQuizScores2.size());

        // Check that the values for the user score record are correct.
        assertEquals(3, userQuizScores2.getLast().getUserQuizScoreID());
        assertNotNull(userQuizScores2.getLast().getUser());
        assertEquals(0, userQuizScores2.getLast().getUser().getUserID());
        assertNotNull(userQuizScores2.getLast().getQuiz());
        assertEquals(0, userQuizScores2.getLast().getQuiz().getQuizID());
        assertEquals(1, userQuizScores2.getLast().getNumCorrect());
        assertEquals(119, userQuizScores2.getLast().getTimeTaken());
        assertEquals(score, userQuizScores2.getLast().getScore());
        assertEquals(100, userQuizScores2.getLast().getScore());
    }
}
