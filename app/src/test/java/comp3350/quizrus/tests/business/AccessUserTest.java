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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
}
