package comp3350.quizrus.tests.business;

import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.business.AccessQuizzes;
import comp3350.quizrus.objects.Quiz;
import comp3350.quizrus.persistence.stubs.QuizPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class AccessQuizTest {
    private AccessQuizzes accessQuizzes;

    @Before
    public void setUp()
    {
        this.accessQuizzes = new AccessQuizzes(new QuizPersistenceStub());
    }

    @Test
    public void test()
    {
        List<Quiz> quizzes = accessQuizzes.getQuizzes();

        System.out.println("Testing that a list of users is returned correctly");
        assertNotNull(quizzes);
        assertTrue(quizzes.size() == 2);

        System.out.println("Testing that users in list are as expected");
        assertTrue(0 == quizzes.get(0).getQuizID());
        assertTrue(1 == quizzes.get(1).getQuizID());

        System.out.println("Finished AccessUserTest");
    }
}
