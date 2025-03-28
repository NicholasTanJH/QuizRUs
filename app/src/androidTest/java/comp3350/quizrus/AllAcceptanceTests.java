package comp3350.quizrus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddQuizTest.class,
        AttemptQuizTest.class,
        DeleteQuizTest.class,
        SearchQuizzesTest.class,
        SignInOutTest.class,
        SignupTest.class,
        TimerTest.class,
        ViewQuizzesTest.class
})

public class AllAcceptanceTests {
}


