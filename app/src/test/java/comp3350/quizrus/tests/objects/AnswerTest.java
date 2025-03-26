package comp3350.quizrus.tests.objects;

import org.junit.Test;
import comp3350.quizrus.objects.Answer;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.Quiz;
import static org.junit.Assert.*;

public class AnswerTest {
    @Test
    public void testAnswer1() {
        Quiz quiz = new Quiz(1, "Sample Quiz", null, 120);
        Question question = new Question(1, "Sample Question", quiz, "Multiple Choice");
        Answer answer = new Answer(1, "Sample Answer", true, question);

        System.out.println("\nStarting testAnswer1");

        assertNotNull(answer);
        assertEquals(1, answer.getAnswerID());
        assertEquals("Sample Answer", answer.getAnswerText());
        assertEquals(question, answer.getMyQuestion());
        assertTrue(answer.isCorrect());

        System.out.println("Finished testAnswer1");
    }

    @Test
    public void testAnswerComparison() {
        Quiz quiz = new Quiz(2, "Comparison Quiz", null, 120);
        Question question = new Question(2, "Comparison Question", quiz, "True/False");
        Answer answer1 = new Answer(2, "Answer A", true, question);
        Answer answer2 = new Answer(2, "Answer A", true, question);
        Answer answer3 = new Answer(3, "Answer B", false, question);

        System.out.println("\nStarting testAnswerComparison");

        assertNotNull(answer1);
        assertNotNull(answer2);
        assertNotNull(answer3);
        assertTrue(answer1.equals(answer2));
        assertFalse(answer1.equals(answer3));

        System.out.println("Finished testAnswerComparison");
    }
}
