package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccessQuizTest {
    private AccessQuizzes accessQuizzes;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        QuizPersistence quizPersistence = Services.getQuizPersistence();
        if (quizPersistence instanceof QuizPersistenceStub) {
            quizPersistence = new QuizPersistenceStub();
        }
        this.accessQuizzes = new AccessQuizzes(quizPersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void test() {
        List<Quiz> quizzes = accessQuizzes.getQuizzes();

        System.out.println("Testing that a list of users is returned correctly");
        assertNotNull(quizzes);
        assertTrue(quizzes.size() == 2);

        System.out.println("Testing that users in list are as expected");
        assertTrue(0 == quizzes.get(0).getQuizID());
        assertTrue(1 == quizzes.get(1).getQuizID());

        System.out.println("Finished AccessUserTest");
    }

    @Test
    public void testDeleteQuiz_Success() {
        User user = new User(1, "testUser", "password", "test", "test");
        Quiz quiz = new Quiz(1, "test", user, 10);
        boolean result = accessQuizzes.deleteQuiz(quiz, user);
        assertTrue(result);
    }

    @Test
    public void testDeleteQuiz_Failure_UnauthorizedUser() {
        User owner = new User(1, "ownerUser", "password", "test", "test");
        User anotherUser = new User(2, "anotherUser", "password", "test", "test");
        Quiz quiz = new Quiz(1, "test", owner, 10);
        boolean result = accessQuizzes.deleteQuiz(quiz, anotherUser);
        assertFalse(result);
    }
}
