package comp3350.quizrus;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.quizrus.presentation.UserLoginActivity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerTest {
    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Test
    public void runOutQuizTime() {
        // login
        onView(withId(R.id.textInputETUsername)).perform(typeText("kakashi"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.textInputETPassword)).perform(typeText("Password1!"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonLogin)).perform(click());
        sleep(5000);

        // play quiz and let the time out
        onView(withId(R.id.listQuiz)).perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));
        onView(withId(R.id.buttonStart)).perform(click());
        sleep(15000);
    }

}
