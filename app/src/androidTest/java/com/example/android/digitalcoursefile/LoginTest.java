package com.example.android.digitalcoursefile;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);
//    @Test
//    public void clickLoginButton_FailsLogin() throws Exception {
//        onView(withId(R.id.editText)).perform(typeText("shed"));
//        onView(withId(R.id.editText2)).perform(typeText("adm001"), closeSoftKeyboard());
//        onView(withId(R.id.button)).perform(click());
//        onView(withText("invalid credentials")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
//    }

    @Test
    public void clickLoginButton_SuccessfulLogin() throws Exception{
        onView(withId(R.id.editText)).perform(typeText("Vijay"));
        onView(withId(R.id.editText2)).perform(typeText("Sai18"), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
        TimeUnit.MILLISECONDS.sleep(3000);
        onView(withText("Login Successful")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
//
//    @Test
//    public void username_empty() throws Exception{
//        onView(withId(R.id.username_input)).perform(clearText());
//        onView(withId(R.id.password_input1)).perform(typeText("adm001"),closeSoftKeyboard());
//        onView(withId(R.id.login)).perform(click());
//        onView(withText("Enter Username")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void password_empty() throws Exception{
//        onView(withId(R.id.username_input)).perform(typeText("cb.en.adm001"),closeSoftKeyboard());
//        onView(withId(R.id.password_input1)).perform(clearText());
//        onView(withId(R.id.login)).perform(click());
//        onView(withText("Enter Password")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
//    }
}
