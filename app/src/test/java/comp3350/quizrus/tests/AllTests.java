package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessUserTest;
import comp3350.quizrus.tests.business.AccessQuizTest;
import comp3350.quizrus.tests.business.AccessQuestionTest;

import comp3350.quizrus.tests.objects.UserTest;
import comp3350.quizrus.tests.objects.QuizTest;
import comp3350.quizrus.tests.objects.QuestionTest;
import comp3350.quizrus.tests.objects.AnswerTest;

import comp3350.quizrus.tests.business.RandomTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                UserTest.class,
                QuizTest.class,
                QuestionTest.class,
                AnswerTest.class,
                RandomTest.class,
                AccessUserTest.class,
                AccessQuizTest.class,
                AccessQuestionTest.class
})
public class AllTests {
}