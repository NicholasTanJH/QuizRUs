package comp3350.quizrus.tests.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.application.Main;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.QuizPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.DatabaseManager;

public class QuizPersistenceHSQLDBTest {
    private QuizPersistenceHSQLDB quizPersistenceHSQLDB;
    private UserPersistenceHSQLDB userPersistenceHSQLDB;
    private final String initSQLPath = "src/main/assets/db/init.sql";

    @Before
    public void setup() {
        Main.setDBPathName("testdb");
        quizPersistenceHSQLDB = new QuizPersistenceHSQLDB(Main.getDBPathName());
        userPersistenceHSQLDB = new UserPersistenceHSQLDB(Main.getDBPathName());
        DatabaseManager.executeSQLFromFile(initSQLPath);
    }

    @After
    public void tearDown() {
        DatabaseManager.executeSQLFromFile(initSQLPath);
    }

    // Integration tests.

    @Test
    public void testInsertQuiz() {
        // Create a new user.
        User user = new User("test", "password", "test@gmail.com", "Bob", "Test");
        int userID = userPersistenceHSQLDB.insertUser(user);
        assertNotEquals(-1, userID);
        user.setUserID(userID);

        // Insert a quiz into the database.
        Quiz quiz = new Quiz("First quiz", user, -1);
        int quizID = quizPersistenceHSQLDB.insertQuiz(quiz, user);
        assertNotEquals(-1, quizID);
    }

    // @Test
    // public void testInsertQuizInvalidUser() {

    // }
}
