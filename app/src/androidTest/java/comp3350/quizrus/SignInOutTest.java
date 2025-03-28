package comp3350.quizrus;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.quizrus.presentation.UserLoginActivity;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInOutTest {
    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Test
    public void loginAndOut() {
        // login
        onView(withId(R.id.textInputETUsername)).perform(typeText("kakashi"));
        onView(withId(R.id.textInputETPassword)).perform(typeText("Password1!"));
        closeSoftKeyboard();
        onView(withId(R.id.buttonLogin)).perform(click());
        sleep(5000);

        // logout
        onView(withId(R.id.accountImageButton)).perform(click());
        sleep(1000);
        onView(withText("Sign Out"))
                .perform(ViewActions.click());
        sleep(2000);
    }
}
