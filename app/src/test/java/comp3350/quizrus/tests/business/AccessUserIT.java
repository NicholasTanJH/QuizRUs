package comp3350.quizrus.tests.business;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

public class AccessUserIT {
    private AccessUsers accessUsers;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        UserPersistence userPersistence = Services.getUserPersistence();
        if (userPersistence instanceof UserPersistenceStub) {
            userPersistence = new UserPersistenceStub();
        }
        this.accessUsers = new AccessUsers(userPersistence);
    }

    @After
    public void tearDown() {
        // Clear the database.
        this.tempDB.delete();
    }

    @Test
    public void testInsertUser() {
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
    }

    @Test
    public void testInitialData() {
        List<User> users = accessUsers.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserByID() {
        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());

        User user2 = accessUsers.getUser(user1.getUserID());
        assertNotNull(user2);
        assertEquals(user1.getUserID(), user2.getUserID());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users;

        User user1 = accessUsers.createUser("bob", "password", "Bob", "Test");
        assertNotNull(user1);
        assertNotEquals(-1, user1.getUserID());
        User user2 = accessUsers.createUser("zen", "password", "Zen", "Test");
        assertNotNull(user2);
        assertNotEquals(-1, user2.getUserID());

        users = accessUsers.getUsers();
        assertEquals(4, users.size());
        for (User user : users) {
            assertTrue(user.getUsername().equals("bob") || user.getUsername().equals("zen") ||
                    user.getUsername().equals("demo") || user.getUsername().equals("kakashi"));
        }
    }

    @Test
    public void testLoginUserSuccess() {
        User user1 = accessUsers.createUser("bob", "Password1!", "Bob", "Test");
        assertNotNull(user1);

        User loggedInUser = accessUsers.loginUser("bob", "Password1!");
        assertNotNull(loggedInUser);
        assertEquals(user1.getUserID(), loggedInUser.getUserID());
    }

    @Test
    public void testLoginUserFailure() {
        User user1 = accessUsers.createUser("bob", "Password1!", "Bob", "Test");
        assertNotNull(user1);

        User loggedInUser = accessUsers.loginUser("bob", "wrongPassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testAuthenticateUsernameTaken() {
        accessUsers.createUser("bob", "Password1!", "Bob", "Test");
        String errorMessage = accessUsers.authenticateUsername("bob");
        assertTrue(errorMessage.contains("Username is taken"));
    }

    @Test
    public void testAuthenticateUsernameValid() {
        String errorMessage = accessUsers.authenticateUsername("newuser");
        assertEquals("", errorMessage);
    }

    @Test
    public void testAuthenticatePasswordValid() {
        String errorMessage = accessUsers.authenticatePassword("Password1!");
        assertEquals("", errorMessage);
    }

    @Test
    public void testAuthenticatePasswordInvalid() {
        String errorMessage = accessUsers.authenticatePassword("pass");
        assertTrue(errorMessage.contains("8 or more characters"));
        assertTrue(errorMessage.contains("Upper case (A-Z)"));
        assertTrue(errorMessage.contains("Number (0-9)"));
        assertTrue(errorMessage.contains("Special character"));
    }

    @Test
    public void testAuthenticateNameValid() {
        assertTrue(accessUsers.authenticateName("Bob"));
    }

    @Test
    public void testAuthenticateNameInvalid() {
        assertFalse(accessUsers.authenticateName(""));
    }

}
