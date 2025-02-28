package comp3350.quizrus.tests.persistence;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.QuizPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.persistence.hsqldb.DatabaseManager;

public class QuizPersistenceHSQLDBTest {
    // Set the path to the database and the path to the SQL file that creates
    // our tables.
    private static final String dbPath = "src/test/java/comp3350/quizrus/tests/persistence/db/testdb";
    private static final String initPath = "src/main/assets/db/test.sql";

    private DatabaseManager dbManager;
    private QuizPersistenceHSQLDB quizPersistenceHSQLDB;
    private UserPersistenceHSQLDB userPersistenceHSQLDB;

    @Before
    public void setup() {
        dbManager = new DatabaseManager(dbPath, initPath, null);
        quizPersistenceHSQLDB = new QuizPersistenceHSQLDB(dbManager);
        userPersistenceHSQLDB = new UserPersistenceHSQLDB(dbManager);
    }

    @After
    public void tearDown() {
        dbManager.dropTables();
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
}
