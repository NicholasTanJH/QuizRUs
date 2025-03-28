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
public class SearchQuizzesTest {
    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Test
    public void SearchQuizzes() {
        // login
        onView(withId(R.id.textInputETUsername)).perform(typeText("kakashi"));
        onView(withId(R.id.textInputETPassword)).perform(typeText("Password1!"));
        closeSoftKeyboard();
        onView(withId(R.id.buttonLogin)).perform(click());
        sleep(5000);

        // use the search bar
        onView(withId(R.id.searchInput)).perform(typeText("Flags of Countries"));
        closeSoftKeyboard();
        onView(withId(R.id.searchButton)).perform(click());
        sleep(5000);

        // clear search
        onView(withId(R.id.searchResultCancerButton)).perform(click());
        sleep(5000);

        // use search bar again
        onView(withId(R.id.searchInput)).perform(typeText("Part"));
        closeSoftKeyboard();
        onView(withId(R.id.searchButton)).perform(click());
        sleep(5000);
    }
}
