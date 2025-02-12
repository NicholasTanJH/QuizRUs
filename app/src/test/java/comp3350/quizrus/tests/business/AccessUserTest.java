package comp3350.quizrus.tests.business;

import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.business.AccessUsers;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.persistence.stubs.UserPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class AccessUserTest {
    private AccessUsers accessUsers;

    public void setUp()
    {
        this.accessUsers = new AccessUsers(new UserPersistenceStub());
    }

    public void test()
    {
        List<User> users = accessUsers.getUsers();

        System.out.println("Testing that a list of users is returned correctly");
        assertNotNull(users);
        assertTrue(users.size() == 5);

        System.out.println("Testing that users in list are as expected");
        assertTrue(1 == users.get(0).getUserID());
        assertTrue(2 == users.get(1).getUserID());
        assertTrue(3 == users.get(2).getUserID());
        assertTrue(4 == users.get(3).getUserID());
        assertTrue(5 == users.get(4).getUserID());

        System.out.println("Finished AccessUserTest");
    }
}
