package comp3350.quizrus;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.quizrus.objects.User;
import comp3350.quizrus.presentation.QuizSelectionActivity;
import comp3350.quizrus.presentation.UserLoginActivity;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddQuizTest {
    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Test
    public void AttemptQuiz() {
        //login
        onView(withId(R.id.textInputETUsername)).perform(typeText("kakashi"));
        onView(withId(R.id.textInputETPassword)).perform(typeText("Password1!"));
        onView(withId(R.id.buttonLogin)).perform(click());
        sleep(5000);

        //create quiz
        onView(withId(R.id.newQuizButton)).perform(click());
        sleep(1000);
        onView(withId(R.id.buttonBackCreateQuiz)).perform(click());
        sleep(1000);
        onView(withId(R.id.newQuizButton)).perform(click());
        sleep(1000);
        onView(withId(R.id.buttonEditQuestion)).perform(click());
        sleep(1000);
        onView(withText("OK")).perform(ViewActions.click());
        sleep(500);
        onView(withId(R.id.QuizNameInput)).perform(typeText("System Test Quiz"));
        onView(withId(R.id.TimerInput)).perform(typeText("10"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonEditQuestion)).perform(click());
        sleep(1000);
        onView(withId(R.id.buttonSave)).perform(click());
        sleep(500);
        onView(withText("OK")).perform(ViewActions.click());
        sleep(500);
        onView(withId(R.id.QuestionInput)).perform(typeText("Question 1"));
        onView(withId(R.id.CorrectAnswerInput)).perform(typeText("A 1"));
        onView(withId(R.id.WrongAnswerInputOne)).perform(typeText("A 2"));
        Espresso.closeSoftKeyboard();
        sleep(1000);
        onView(withId(R.id.buttonSave)).perform(click());
        sleep(500);
        onView(withText("OK")).perform(ViewActions.click());
        sleep(500);
        onView(withId(R.id.WrongAnswerInputTwo)).perform(typeText("A 3"));
        onView(withId(R.id.WrongAnswerInputThree)).perform(typeText("A 4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSave)).perform(click());
        sleep(1000);
        onView(withId(R.id.QuestionInput)).perform(typeText("Question 2"));
        onView(withId(R.id.CorrectAnswerInput)).perform(typeText("A 1"));
        onView(withId(R.id.WrongAnswerInputOne)).perform(typeText("A 2"));
        onView(withId(R.id.WrongAnswerInputTwo)).perform(typeText("A 3"));
        onView(withId(R.id.WrongAnswerInputThree)).perform(typeText("A 4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSave)).perform(click());
        sleep(500);
        onView(withId(R.id.buttonDoneEditQuestion)).perform(click());
        sleep(2000);
    }
}
