package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessAnswerIT;
import comp3350.quizrus.tests.business.AccessAnswerTest;
import comp3350.quizrus.tests.business.AccessQuestionIT;
import comp3350.quizrus.tests.business.AccessQuizIT;
import comp3350.quizrus.tests.business.AccessUserIT;
import comp3350.quizrus.tests.business.AccessUserTest;
import comp3350.quizrus.tests.business.AccessQuizTest;
import comp3350.quizrus.tests.business.AccessQuestionTest;

import comp3350.quizrus.tests.objects.UserTest;
import comp3350.quizrus.tests.objects.QuizTest;
import comp3350.quizrus.tests.objects.QuestionTest;
import comp3350.quizrus.tests.objects.AnswerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserTest.class,
        QuizTest.class,
        QuestionTest.class,
        AnswerTest.class,
        AccessUserTest.class,
        AccessQuizTest.class,
        AccessQuestionTest.class,
        AccessAnswerTest.class,
        AccessAnswerIT.class,
        AccessQuestionIT.class,
        AccessQuizIT.class,
        AccessUserIT.class
})
public class AllTests {
}