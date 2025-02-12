package comp3350.quizrus.tests.objects;

import org.junit.Test;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import static org.junit.Assert.*;

public class QuestionTest {
    @Test
    public void testQuestion1() {
        Quiz quiz = new Quiz(1, "Sample Quiz", null);
        Question question = new Question(1, "Sample Question", quiz, "Multiple Choice");

        System.out.println("\nStarting testQuestion1");

        assertNotNull(question);
        assertEquals(1, question.getQuestionID());
        assertEquals("Sample Question", question.getQuestionText());
        assertEquals(quiz, question.getMyQuiz());
        assertEquals("Multiple Choice", question.getQuestionType());

        System.out.println("Finished testQuestion1");
    }

    @Test
    public void testQuestionComparison() {
        Quiz quiz = new Quiz(2, "Comparison Quiz", null);
        Question question1 = new Question(2, "Question A", quiz, "True/False");
        Question question2 = new Question(2, "Question A", quiz, "True/False");
        Question question3 = new Question(3, "Question B", quiz, "Short Answer");

        System.out.println("\nStarting testQuestionComparison");

        assertNotNull(question1);
        assertNotNull(question2);
        assertNotNull(question3);
        assertTrue(question1.equals(question2));
        assertFalse(question1.equals(question3));
        assertEquals(question1.hashCode(), question2.hashCode());
        assertNotEquals(question1.hashCode(), question3.hashCode());

        System.out.println("Finished testQuestionComparison");
    }
}
