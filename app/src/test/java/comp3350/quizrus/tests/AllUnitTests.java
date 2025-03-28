package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessAnswerTest;
import comp3350.quizrus.tests.business.AccessLeaderboardTest;
import comp3350.quizrus.tests.business.AccessUserTest;
import comp3350.quizrus.tests.business.AccessQuizTest;
import comp3350.quizrus.tests.business.AccessQuestionTest;

import comp3350.quizrus.tests.business.CalculateScoreTest;
import comp3350.quizrus.tests.objects.UserQuizScoreTest;
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
        UserQuizScoreTest.class,
        AccessUserTest.class,
        AccessQuizTest.class,
        AccessQuestionTest.class,
        AccessAnswerTest.class,
        CalculateScoreTest.class,
        AccessLeaderboardTest.class,
})
public class AllUnitTests {
}