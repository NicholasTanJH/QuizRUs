package comp3350.quizrus.tests.persistence;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.persistence.hsqldb.DatabaseManager;

public class UserPersistenceHSQLDBTest {
    // Set the path to the database and the path to the SQL file that creates
    // our tables.
    private static final String dbPath = "src/test/java/comp3350/quizrus/tests/persistence/db/testdb";
    private static final String initPath = "src/main/assets/db/test.sql";

    private DatabaseManager dbManager;
    private UserPersistenceHSQLDB userPersistenceHSQLDB;

    @Before
    public void setup() {
//        dbManager = new DatabaseManager(dbPath, initPath, null);
//        userPersistenceHSQLDB = new UserPersistenceHSQLDB(dbManager);
    }

    @After
//    public void tearDown() {
//        dbManager.dropTables();
//    }

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

    @Test(expected = PersistenceException.class)
    public void testInsertSameEmailFail() {
        User user1 = new User("test", "password", "test@gmail.com", "Bob", "Test");
        User user2 = new User("zen", "password", "test@gmail.com", "Bob", "Test");

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
