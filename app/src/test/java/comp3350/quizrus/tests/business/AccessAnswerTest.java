package comp3350.quizrus.tests.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.AccessAnswers;
import comp3350.quizrus.objects.Answer;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AccessAnswerTest {

    @Test
    public void testGetCorrectAnswerPosition(){
        List<Answer> answerList = new ArrayList<>();
        AccessAnswers test = new AccessAnswers();
        Answer answer1 = new Answer(0, "Answer A", true, null);
        Answer answer2 = new Answer(1, "Answer B", false, null);
        Answer answer3 = new Answer(2, "Answer C", false, null);
        Answer answer4 = new Answer(3, "Answer D", false, null);
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        answerList.add(answer4);

        int correctPosition = test.getCorrectAnswerPosition(answerList);
        assertEquals(0, correctPosition);
        assertNotEquals(1, correctPosition);
    }
}
