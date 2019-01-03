package ru.divizdev.coinrate.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.rates.PreferenceManagerSettings;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ru.divizdev.coinrate.ui.Utils.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CoinRateActivityTest {

    @Rule
    public ActivityTestRule<CoinRateActivity> _activityTestRule = new ActivityTestRule<>(CoinRateActivity.class, true, false);

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

    @Test
    public void coinRateActivityTest() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.menu_item_rub));

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());

        onView(withRecyclerView(R.id.coin_rate_list)
                .atPositionOnView(1, R.id.currency_rate_coin))
                .check(matches(withText("\u20BD")));

    }

    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
        // Check that left drawer is closed at startup
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START))); // Left Drawer should be closed.

        // Open Drawer
        String navigateUpDesc = _activityTestRule.getActivity()
                .getString(android.support.v7.appcompat.R.string.abc_action_bar_up_description);
        onView(withContentDescription(navigateUpDesc)).perform(click());

        // Check if drawer is open
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.START))); // Left drawer is open open.
    }

    @Before
    public void registerIdlingResource() {

        Context targetContext = getInstrumentation().getTargetContext();
        Editor preferencesEditor = PreferenceManager.getDefaultSharedPreferences(targetContext).edit();

        preferencesEditor.putString(PreferenceManagerSettings.KEY_NAME_PREF, "USD");
        preferencesEditor.commit();

         _activityTestRule.launchActivity(new Intent());

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
