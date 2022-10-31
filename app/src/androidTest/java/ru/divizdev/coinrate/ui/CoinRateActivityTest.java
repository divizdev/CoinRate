package ru.divizdev.coinrate.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static ru.divizdev.coinrate.ui.Utils.withRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import ru.divizdev.coinrate.R;
import ru.divizdev.coinrate.data.PreferenceManagerSettings;
import ru.divizdev.coinrate.presentation.main.CoinRateActivity;
import ru.divizdev.coinrate.utils.EspressoIdlingResource;

@LargeTest

@RunWith(AndroidJUnit4.class)
public class CoinRateActivityTest {

    private static final String BODY_USD = "{\n" +
            "\"data\": [\n" +
            "{\n" +
            "\"id\": 1,\n" +
            "\"name\": \"Bitcoin\",\n" +
            "\"symbol\": \"BTC\",\n" +
            "\"slug\": \"bitcoin\",\n" +
            "\"cmc_rank\": 5,\n" +
            "\"num_market_pairs\": 500,\n" +
            "\"circulating_supply\": 16950100,\n" +
            "\"total_supply\": 16950100,\n" +
            "\"max_supply\": 21000000,\n" +
            "\"last_updated\": \"2018-06-02T22:51:28.209Z\",\n" +
            "\"date_added\": \"2013-04-28T00:00:00.000Z\",\n" +
            "\"tags\": [\n" +
            "\"mineable\"\n" +
            "],\n" +
            "\"platform\": null,\n" +
            "\"quote\": {\n" +
            "\"USD\": {\n" +
            "\"price\": 9283.92,\n" +
            "\"volume_24h\": 7155680000,\n" +
            "\"percent_change_1h\": -0.152774,\n" +
            "\"percent_change_24h\": 0.518894,\n" +
            "\"percent_change_7d\": 0.986573,\n" +
            "\"market_cap\": 158055024432,\n" +
            "\"last_updated\": \"2018-08-09T22:53:32.000Z\"\n" +
            "},\n" +
            "\"BTC\": {\n" +
            "\"price\": 1,\n" +
            "\"volume_24h\": 772012,\n" +
            "\"percent_change_1h\": 0,\n" +
            "\"percent_change_24h\": 0,\n" +
            "\"percent_change_7d\": 0,\n" +
            "\"market_cap\": 17024600,\n" +
            "\"last_updated\": \"2018-08-09T22:53:32.000Z\"\n" +
            "}\n" +
            "}\n" +
            "}\n" +
            "],\n" +
            "\"status\": {\n" +
            "\"timestamp\": \"2018-06-02T22:51:28.209Z\",\n" +
            "\"error_code\": 0,\n" +
            "\"error_message\": \"\",\n" +
            "\"elapsed\": 10,\n" +
            "\"credit_count\": 1\n" +
            "}\n" +
            "}";

    private static final String BODY_RUB = "{\n" +
            "\"data\": [\n" +
            "{\n" +
            "\"id\": 1,\n" +
            "\"name\": \"Bitcoin\",\n" +
            "\"symbol\": \"BTC\",\n" +
            "\"slug\": \"bitcoin\",\n" +
            "\"cmc_rank\": 5,\n" +
            "\"num_market_pairs\": 500,\n" +
            "\"circulating_supply\": 16950100,\n" +
            "\"total_supply\": 16950100,\n" +
            "\"max_supply\": 21000000,\n" +
            "\"last_updated\": \"2018-06-02T22:51:28.209Z\",\n" +
            "\"date_added\": \"2013-04-28T00:00:00.000Z\",\n" +
            "\"tags\": [\n" +
            "\"mineable\"\n" +
            "],\n" +
            "\"platform\": null,\n" +
            "\"quote\": {\n" +
            "\"RUB\": {\n" +
            "\"price\": 9283.92,\n" +
            "\"volume_24h\": 7155680000,\n" +
            "\"percent_change_1h\": -0.152774,\n" +
            "\"percent_change_24h\": 0.518894,\n" +
            "\"percent_change_7d\": 0.986573,\n" +
            "\"market_cap\": 158055024432,\n" +
            "\"last_updated\": \"2018-08-09T22:53:32.000Z\"\n" +
            "},\n" +
            "\"BTC\": {\n" +
            "\"price\": 1,\n" +
            "\"volume_24h\": 772012,\n" +
            "\"percent_change_1h\": 0,\n" +
            "\"percent_change_24h\": 0,\n" +
            "\"percent_change_7d\": 0,\n" +
            "\"market_cap\": 17024600,\n" +
            "\"last_updated\": \"2018-08-09T22:53:32.000Z\"\n" +
            "}\n" +
            "}\n" +
            "}\n" +
            "],\n" +
            "\"status\": {\n" +
            "\"timestamp\": \"2018-06-02T22:51:28.209Z\",\n" +
            "\"error_code\": 0,\n" +
            "\"error_message\": \"\",\n" +
            "\"elapsed\": 10,\n" +
            "\"credit_count\": 1\n" +
            "}\n" +
            "}";
    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

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
    public void navToDetailTest() {
        onView(withRecyclerView(R.id.coin_rate_list)
                .atPositionOnView(0, R.id.currency_rate_coin)).perform(click());

        onView(withId(R.id.detail_name_coin)).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrencyTest() {

//        mockWebServerRule.server.enqueue(new MockResponse().setBody(BODY_RUB));
//        onView(withId(R.id.drawer_layout))
//                .check(matches(isClosed(Gravity.START)))
//                .perform(DrawerActions.open()); // Open Drawer
//
//        // Start the screen of your activity.
//        onView(withId(R.id.nav_view))
//                .perform(NavigationViewActions.navigateTo(R.id.menu_item_rub));
//
//
//        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
//
//        onView(withRecyclerView(R.id.coin_rate_list)
//                .atPositionOnView(0, R.id.currency_rate_coin))
//                .check(matches(withText("\u20BD")));

    }

    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
//        // Check that left drawer is closed at startup
//        onView(withId(R.id.drawer_layout))
//                .check(matches(isClosed(Gravity.START))); // Left Drawer should be closed.
//
//        // Open Drawer
//        String navigateUpDesc = _activityTestRule.getActivity()
//                .getString(android.support.v7.appcompat.R.string.abc_action_bar_up_description);
//        onView(withContentDescription(navigateUpDesc)).perform(click());
//
//        // Check if drawer is open
//        onView(withId(R.id.drawer_layout))
//                .check(matches(isOpen(Gravity.START))); // Left drawer is open open.
    }

    @Before
    public void registerIdlingResource() throws IOException {


        Context targetContext = getInstrumentation().getTargetContext();
        ru.divizdev.coinrate.di.Factory.getConfig().setBaseURL(mockWebServerRule.server.url("/").toString());
        mockWebServerRule.server.enqueue(new MockResponse().setBody(BODY_USD));


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
