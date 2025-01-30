package comp3350.quizrus.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.quizrus.tests.business.AccessCoursesTest;
import comp3350.quizrus.tests.business.AccessSCTest;
import comp3350.quizrus.tests.business.AccessStudentsTest;
import comp3350.quizrus.tests.objects.CourseTest;
import comp3350.quizrus.tests.objects.SCTest;
import comp3350.quizrus.tests.objects.StudentTest;
import comp3350.quizrus.tests.business.CalculateGPATest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StudentTest.class,
        CourseTest.class,
        SCTest.class,
        CalculateGPATest.class,
        AccessCoursesTest.class,
        AccessStudentsTest.class,
        AccessSCTest.class
})
public class AllTests
{

}
