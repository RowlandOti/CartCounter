package com.rowland.cartcounter.sample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CartCounterTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void cartCounterTest() {
        ViewInteraction cartCounterActionView = onView(
                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        cartCounterActionView.perform(click());

        ViewInteraction cartCounterActionView2 = onView(
                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        cartCounterActionView2.perform(click());

        ViewInteraction cartCounterActionView3 = onView(
                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        cartCounterActionView3.perform(click());

        ViewInteraction cartCounterActionView4 = onView(
                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        cartCounterActionView4.perform(click());

        ViewInteraction cartCounterActionView5 = onView(
                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        cartCounterActionView5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.counterTextView), withText("5"), withContentDescription("CounterTextView"),
                        childAtPosition(
                                allOf(withId(R.id.action_addcart), withContentDescription("Add Cart"),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.support.v7.widget.LinearLayoutCompat.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("5")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
