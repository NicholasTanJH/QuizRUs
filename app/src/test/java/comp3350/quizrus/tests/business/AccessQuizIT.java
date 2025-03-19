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
import comp3350.quizrus.persistence.QuizPersistence;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessQuizIT {
    private AccessQuizzes accessQuizzes;
    private AccessUsers accessUsers;
    private File tempDB;

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
        this.accessQuizzes = new AccessQuizzes(quizPersistence);
        this.accessUsers = new AccessUsers(userPersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertQuiz() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Insert a quiz into the database.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "What is the life expectancy in Canada?", 120);
        assertNotNull(quiz1);
        int quiz1ID = quiz1.getQuizID();
        assertNotEquals(-1, quiz1ID);
    }

    @Test
    public void testGetQuizByID() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Insert a quiz into the database.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "What is the life expectancy in Canada?", 120);
        assertNotNull(quiz1);
        int quiz1ID = quiz1.getQuizID();
        assertNotEquals(-1, quiz1ID);

        // Retrieve the quiz from the database.
        Quiz quiz2 = accessQuizzes.getQuiz(quiz1ID);
        assertNotNull(quiz2);
        assertEquals(quiz1.getQuizID(), quiz2.getQuizID());
        assertEquals(user1.getUserID(), quiz2.getUser().getUserID());
        assertEquals("What is the life expectancy in Canada?", quiz2.getTitle());
        assertEquals(120, quiz2.getTimeLimit());
    }

    @Test
    public void testGetQuizzesEmptyList() {
        // Ensure the database is empty.
        List<Quiz> quizzes = accessQuizzes.getQuizzes();
        assertNotNull(quizzes);
        // 2 Default quizzes
        assertEquals(2, quizzes.size());
    }

    @Test
    public void testDeleteQuiz() {
        // Create users.
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
        User user2 = accessUsers.createUser("zen", "password", "Zen", "Test");
        assertNotNull(user2);
        assertNotEquals(-1, user2.getUserID());

        // Insert a quiz into the database.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "What is the life expectancy in Canada?", 120);
        assertNotNull(quiz1);
        int quiz1ID = quiz1.getQuizID();
        assertNotEquals(-1, quiz1ID);

        // Attempt to delete the quiz with the wrong user.
        boolean deleted = accessQuizzes.deleteQuiz(quiz1, user2);
        assertFalse(deleted);

        // Delete the quiz with the right user.
        boolean deleted2 = accessQuizzes.deleteQuiz(quiz1, user1);
        assertTrue(deleted2);
        Quiz quiz2 = accessQuizzes.getQuiz(quiz1.getQuizID());
        assertNull(quiz2);
    }

    @Test
    public void testQuizOnDeleteCascade() {

    }
}
