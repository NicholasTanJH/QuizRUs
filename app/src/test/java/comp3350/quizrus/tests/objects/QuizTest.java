package comp3350.quizrus.tests.objects;

import org.junit.Test;

import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.objects.User;
import static org.junit.Assert.*;

public class QuizTest {

    @Test
    public void testQuiz() {
        User user1 = new User(10, "naruto", "ilovesasuke");
        Quiz quiz1;

        System.out.println("\nStarting QuizTest");

        quiz1 = new Quiz(420, "history of fortnite", user1);
        Quiz quiz2 = quiz1;

        System.out.println("Testing if a quiz can be created.");
        assertNotNull(quiz1);

        System.out.println("Testing if a quizID is set and returned correctly.");
        assertTrue(quiz1.getQuizID() == 420);

        System.out.println("Testing if a title is set and returned correctly.");
        assertTrue(quiz1.getTitle().equals("history of fortnite"));

        System.out.println("Testing if the equals function for quizzes works correctly.");
        assertTrue(quiz1.equals(quiz2));

        System.out.println("Finished testQuiz");
        System.out.println();

    }
}
