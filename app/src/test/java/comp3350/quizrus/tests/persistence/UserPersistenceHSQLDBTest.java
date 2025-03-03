package comp3350.quizrus.tests.persistence;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.application.Main;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.persistence.hsqldb.DatabaseManager;

public class UserPersistenceHSQLDBTest {
    private UserPersistenceHSQLDB userPersistenceHSQLDB;
    private final String initSQLPath = "src/main/assets/db/init.sql";

    @Before
    public void setup() {
        Main.setDBPathName("testdb");
        userPersistenceHSQLDB = new UserPersistenceHSQLDB(Main.getDBPathName());
        DatabaseManager.executeSQLFromFile(initSQLPath);
    }

    @After
    public void tearDown() {
        DatabaseManager.executeSQLFromFile(initSQLPath);
    }

    // Unit tests.

    @Test
    public void testInsertUser() {
        User user = new User("test", "password", "test@gmail.com", "Bob", "Test");

        int userID = userPersistenceHSQLDB.insertUser(user);
        assertNotEquals(-1, userID);
    }

    @Test(expected = PersistenceException.class)
    public void testInsertSameUsernameFail() {
        User user1 = new User("test", "password", "test@gmail.com", "Bob", "Test");
        User user2 = new User("test", "password", "zen@gmail.com", "Bob", "Test");

        int userID = userPersistenceHSQLDB.insertUser(user1);
        assertNotEquals(-1, userID);
        userPersistenceHSQLDB.insertUser(user2);
    }

    @Test
    public void testEmptyDBRetrieval() {
        List<User> users = userPersistenceHSQLDB.getAllUsers();
        assertEquals(0, users.size());
    }

    @Test
    public void testGetUserByID() {
        User user1 = new User("test", "password", "test@gmail.com", "Bob", "Test");

        int userID = userPersistenceHSQLDB.insertUser(user1);
        assertNotEquals(-1, userID);

        User user = userPersistenceHSQLDB.getUserByID(userID);
        assertNotNull(user);
        assertEquals(0, user.getUserID());
    }

    @Test
    public void testGetUserByUsername() {
        User user1 = new User("test", "password", "test@gmail.com", "Bob", "Test");

        int userID = userPersistenceHSQLDB.insertUser(user1);
        assertNotEquals(-1, userID);

        User user = userPersistenceHSQLDB.getUserByUsername("test");
        assertNotNull(user);
        assertEquals(0, user.getUserID());
    }

    // Integration tests.

    @Test
    public void testInsertAndRetrieval() {
        List<User> users;

        User user1 = new User("test", "password", "test@gmail.com", "Bob", "Test");
        User user2 = new User("zen", "password", "zen@gmail.com", "Bob", "Test");

        int user1ID = userPersistenceHSQLDB.insertUser(user1);
        assertNotEquals(-1, user1ID);
        int user2ID = userPersistenceHSQLDB.insertUser(user2);
        assertNotEquals(-1, user2ID);

        users = userPersistenceHSQLDB.getAllUsers();
        assertEquals(2, users.size());
        for (User user : users) {
            assertTrue(user.getUsername().equals("test") || user.getUsername().equals("zen"));
        }
    }
}
