package comp3350.quizrus.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.application.Services;
import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.UserPersistence;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;
import comp3350.quizrus.tests.utils.TestUtils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccessUserTest {
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
    public void test() {
        List<User> users = accessUsers.getUsers();

        System.out.println("Testing that a list of users is returned correctly");
        assertNotNull(users);
        assertTrue(users.size() == 2);

        System.out.println("Testing that users in list are as expected");
        assertTrue(0 == users.get(0).getUserID());
        assertTrue(1 == users.get(1).getUserID());

        System.out.println("Testing that users are not the same");
        User user1 = users.get(0);
        User user2 = users.get(1);
        assertTrue(!(user1.equals(user2)));

        System.out.println("Finished AccessUserTest");
    }

    @Test
    public void testAuthenticateUser() {
        String error = accessUsers.authenticateUser("kakashi", "Hello1!", "Hello1!", "Saige", "Santana");
        assertEquals("Username must be:\n\n \t - Username is taken", error);

        String error1 = accessUsers.authenticateUser("kakashishishishishishishishishishishishishi", "Hello", "Hello",
                "Saige", "Santana");
        assertEquals(error1, "Username must be:\n\n \t - 20 characters or shorter");

        String error2 = accessUsers.authenticateUser("", "Hello1!!", "Hello1!!", "Saige", "Santana");
        assertEquals(error2, "Username must be:\n\n \t - 20 characters or shorter");

        String error3 = accessUsers.authenticateUser("kakashi1!", "Hello", "Hello", "Saige", "Santana");
        assertEquals(error3,
                "Password must have:\n\n \t - 8 or more characters\n \t - Number (0-9)\n \t - Special character (eg. !@#$%^&*()_+)");
    }
}
