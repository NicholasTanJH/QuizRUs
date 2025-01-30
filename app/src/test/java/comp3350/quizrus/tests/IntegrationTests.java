package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessCoursesIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessCoursesIT.class
})
public class IntegrationTests {
}
