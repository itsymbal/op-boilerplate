package com.orangepenguin.boilerplate.screens.username;

import android.app.Application;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.di.AndroidTestComponentFactory;
import com.orangepenguin.boilerplate.di.Injector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UsernameActivityTest {

    @Rule
    public ActivityTestRule<UsernameActivity> activityRule = new ActivityTestRule<>(UsernameActivity.class);

    @Before
    public void setUp() throws Exception {

        //        activityRule.getActivity();
        Application application = activityRule.getActivity().getApplication();
        Injector.setComponentFactory(new AndroidTestComponentFactory((com.orangepenguin.boilerplate.Application)
                application));
    }

    @Test
    public void dontDoNothing() {
        onView(withId(R.id.username_edit_text))
                .perform(typeText("itsymbal"), closeSoftKeyboard());
        onView(withId(R.id.view_user_button)).perform(click());
    }
}
