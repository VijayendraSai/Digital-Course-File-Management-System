package com.example.android.digitalcoursefile;

import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
@RunWith(AndroidJUnit4.class)
public class Search_Files_Test {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnYourNavigationItem_Files() throws InterruptedException {
        onView(withId(R.id.editText)).perform(typeText("Vijay"));
        onView(withId(R.id.editText2)).perform(typeText("Sai18"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        TimeUnit.MILLISECONDS.sleep(1500);

        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        TimeUnit.MILLISECONDS.sleep(1500);

        // Start the screen of your activity.
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_files));

        TimeUnit.MILLISECONDS.sleep(1500);

        onView(withId(R.id.button38)).perform(click());
        TimeUnit.MILLISECONDS.sleep(1500);

        // Type "yuvar" to trigger suggestions.
        onView(withId(R.id.autoCompleteTextView)).perform(typeText("AH"), closeSoftKeyboard());

        // Check that both suggestions are displayed.
        onData(equalTo("AHS")).inRoot(RootMatchers.withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).perform(click());

        onView(withId(R.id.button40)).perform(click());
        TimeUnit.MILLISECONDS.sleep(8000);
    }
}
