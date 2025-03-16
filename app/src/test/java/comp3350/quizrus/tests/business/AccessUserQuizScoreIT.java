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

public class AccessUserQuizScoreIT {
    private AccessQuizzes accessQuizzes;
    private AccessUsers accessUsers;
    private File tempDB;

    UserQuizScorePersistence userQuizScorePersistence = Services.getUserQuizScorePersistence();

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        UserPersistence userPersistence = Services.getUserPersistence();
        if (userPersistence instanceof UserPersistenceStub) {
            userPersistence = new UserPersistenceStub();
        }
        QuizPersistence quizPersistence = Services.getQuizPersistence();
        if (quizPersistence instanceof QuizPersistenceStub) {
            quizPersistence = new QuizPersistenceStub();
        }

        // UserQuizScorePersistence userQuizScorePersistence =
        // Services.getUserQuizScorePersistence();
        // if (userQuizScorePersistence instanceof UserQuizScorePersistenceStub) {
        // userQuizScorePersistence = new UserQuizScorePersistenceStub();
        // }
        this.accessQuizzes = new AccessQuizzes(quizPersistence);
        this.accessUsers = new AccessUsers(userPersistence);
        // this.accessUserQuizScores = new
        // AccessUserQuizScores(userQuizScorePersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testGetLeaderboard() {
        // Get the first quiz.
        Quiz quiz1 = accessQuizzes.getQuiz(0);

        // TODO: Replace this with business logic code.
        List<UserQuizScore> userQuizScores = userQuizScorePersistence.getLeaderboard(quiz1);
        assertEquals(userQuizScores.getFirst().getUserQuizScoreID(), 1);
        assertEquals(userQuizScores.getFirst().getUserID(), 0);
        assertEquals(userQuizScores.getFirst().getQuizID(), 0);
        assertEquals(userQuizScores.getFirst().getScore(), 320);
        assertEquals(userQuizScores.getFirst().getUserQuizScoreID(), 1);
        assertEquals(userQuizScores.getLast().getUserID(), 1);
        assertEquals(userQuizScores.getFirst().getQuizID(), 0);
        assertEquals(userQuizScores.getLast().getScore(), 55);
    }

    @Test
    public void testInsertScore() {

    }
}
