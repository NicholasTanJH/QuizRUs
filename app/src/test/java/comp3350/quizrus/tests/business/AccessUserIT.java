package comp3350.quizrus.tests.business;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.hsqldb.PersistenceException;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessUserIT {
    private AccessUsers accessUsers;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accessUsers = new AccessUsers();
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertUser() {
        User user1 = accessUsers.createUser("bob", "password", "test@gmail.com", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
    }

    @Test(expected = PersistenceException.class)
    public void testInsertSameUsernameFail() {
        User user1 = accessUsers.createUser("bob", "password", "test@gmail.com", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
        accessUsers.createUser("bob", "password", "zen@gmail.com", "Zen", "Test");
    }

    @Test
    public void testInitialData() {
        List<User> users = accessUsers.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserByID() {
        User user1 = accessUsers.createUser("bob", "password", "test@gmail.com", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        User user2 = accessUsers.getUser(user1.getUserID());
        assertNotNull(user2);
        assertEquals(user1.getUserID(), user2.getUserID());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users;

        User user1 = accessUsers.createUser("bob", "password", "test@gmail.com", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
        User user2 = accessUsers.createUser("zen", "password", "zen@gmail.com", "Zen", "Test");
        assertNotNull(user2);
        assertNotEquals(-1, user2.getUserID());

        users = accessUsers.getUsers();
        assertEquals(4, users.size());
        for (User user : users) {
            assertTrue(user.getUsername().equals("bob") || user.getUsername().equals("zen") ||
                    user.getUsername().equals("demo") || user.getUsername().equals("kakashi"));
        }
    }

    // @Test
    // public void testGetUserByUsername() {
    // User user1 = accessUsers.createUser("bob", "password", "test@gmail.com",
    // "Bob", "Test");
    // assertNotNull(user1);
    // assertNotEquals(-1, user1.getUserID());

    // User user2 = accessUsers.getUserByUsername("bob");
    // assertNotNull(user2);
    // assertEquals(user1.getUserID(), user2.getUserID());
    // assertEquals("bob", user2.getUsername());
    // }
}
