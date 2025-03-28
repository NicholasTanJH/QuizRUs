package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessAnswerIT;
import comp3350.quizrus.tests.business.AccessLeaderboardIT;
import comp3350.quizrus.tests.business.AccessQuestionIT;
import comp3350.quizrus.tests.business.AccessQuizIT;
import comp3350.quizrus.tests.business.AccessUserIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessAnswerIT.class,
        AccessQuestionIT.class,
        AccessQuizIT.class,
        AccessUserIT.class,
        AccessLeaderboardIT.class,
})
public class AllIntegrationTests{
}