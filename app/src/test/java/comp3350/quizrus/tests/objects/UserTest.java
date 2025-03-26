package comp3350.quizrus.tests.objects;

import org.junit.Test;

import comp3350.quizrus.objects.User;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUser() {
        User user1;
        System.out.println("\nStarting UserTest");

        user1 = new User(69, "faker", "squidgame", "lee", "sanghyeok");
        User user2 = user1;

        System.out.println("Testing if a user can be created.");
        assertNotNull(user1);

        System.out.println("Testing if a userID is set and returned correctly.");
        assertTrue(user1.getUserID() == 69);

        System.out.println("Testing if a username is set and returned correctly.");
        assertTrue(user1.getUsername().equals("faker"));

        System.out.println("Testing if a password is set and returned correctly.");
        assertTrue(user1.getPassword().equals("squidgame"));

        System.out.println("Testing if the equals function for users works correctly.");
        assertTrue(user1.equals(user2));

        System.out.println("Finished testUser");
        System.out.println();

    }
}
