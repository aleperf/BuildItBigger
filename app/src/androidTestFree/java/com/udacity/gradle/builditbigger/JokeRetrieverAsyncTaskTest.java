package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.not;


public class JokeRetrieverAsyncTaskTest {
    private CountingIdlingResource countingIdlingResource;
    //text displayed if no joke is passed to JokeDisplayActivity
    private String DEFAULT_TEXT_NO_JOKE = "No joke to display";
    private String EMPTY_STRING = "";

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() {
        rule.launchActivity(new Intent());
        countingIdlingResource = rule.getActivity().getCountingIdlingResource();
         IdlingRegistry.getInstance().register(countingIdlingResource);



    }

    @Test
    public void checkJokeTextViewIsDisplayedAndNotEmpty() {
        try{
            Thread.sleep(2000);//give time to the ad for being prepared
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        onView(withId(R.id.joke_button)).perform(click());
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Interstitial close button"), isDisplayed()));
        imageButton.perform(click());
        onView(withId(R.id.joke_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.joke_text_view)).check(matches(not(withText(DEFAULT_TEXT_NO_JOKE))));
        onView(withId(R.id.joke_text_view)).check(matches(not(withText(EMPTY_STRING))));
    }

    @After
    public void cleanUp() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }
}
