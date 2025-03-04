package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessQuizIT {
    private AccessQuizzes accessQuizzes;
    private AccessUsers accessUsers;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accessQuizzes = new AccessQuizzes();
        this.accessUsers = new AccessUsers();
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertQuiz() {
        // Create a new user.
        User user1 = accessUsers.createUser("bob", "password", "test@gmail.com", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        // Insert a quiz into the database.
        Quiz quiz1 = accessQuizzes.createQuiz(user1, "What is the life expectancy in Canada?", 120);
        assertNotNull(quiz1);
        int quiz1ID = quiz1.getQuizID();
        assertNotEquals(-1, quiz1ID);
    }

     @Test(expected = PersistenceException.class)
     public void testInsertQuizInvalidUser() {
         // Create a new user without inserting them into the database..
         User user1 = new User("bob", "password", "test@gmail.com", "Bob", "Test");

         // Insert a quiz into the database.
         accessQuizzes.createQuiz(user1, "What is the life expectancy in Canada?", 120);
     }
}
