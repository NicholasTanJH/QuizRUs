package comp3350.quizrus;
import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.quizrus.presentation.UserLoginActivity;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest {
    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Test
    public void signup() {
        onView(withId(R.id.buttonSignUp)).perform(click());

        // Fill in sign-up details
        onView(withId(R.id.textInputETUsername)).perform(typeText("testUser"));
        onView(withId(R.id.textInputETPassword)).perform(typeText("Password00!"));
        onView(withId(R.id.textInputETConfirmPassword)).perform(typeText("Password00!"));
        onView(withId(R.id.textInputETFirstName)).perform(typeText("John"));
        onView(withId(R.id.textInputETLastName)).perform(typeText("Doe"));
        closeSoftKeyboard();
        // Click sign-up button
        onView(withId(R.id.buttonCreateAccount)).perform(click());
        sleep(5000);
    }

}

