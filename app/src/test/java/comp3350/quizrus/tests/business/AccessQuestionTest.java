package comp3350.quizrus.tests.business;

import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.business.AccessQuestions;
import comp3350.quizrus.objects.Question;
import comp3350.quizrus.objects.User;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.stubs.QuestionPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class AccessQuestionTest {
    private AccessQuestions accessQuestions;

    @Before
    public void setUp() {
        this.accessQuestions = new AccessQuestions(new QuestionPersistenceStub());
    }

    @Test
    public void testGetQuestions() {
        // Creating a quiz to pass to the access questions method
        Quiz quiz1 = new Quiz(0, "Flags of Countries", new User(1, "kakashi", "password1"));

        // Get the list of questions for quiz1
        List<Question> questions = accessQuestions.getQuestions(quiz1);

        System.out.println("Testing that a list of questions is returned correctly");
        assertNotNull(questions);
        assertTrue(questions.size() == 5);  // Assume quiz1 has 5 questions

        System.out.println("Testing that questions in the list are as expected");
        assertTrue(0 == questions.get(0).getQuestionID());
        assertTrue(1 == questions.get(1).getQuestionID());
        assertTrue(2 == questions.get(2).getQuestionID());
        assertTrue(3 == questions.get(3).getQuestionID());
        assertTrue(4 == questions.get(4).getQuestionID());

        System.out.println("Finished testGetQuestions");
    }

}

