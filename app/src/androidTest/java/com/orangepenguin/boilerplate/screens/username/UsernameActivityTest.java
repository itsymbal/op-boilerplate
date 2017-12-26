package com.orangepenguin.boilerplate.screens.username;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.orangepenguin.boilerplate.Application;
import com.orangepenguin.boilerplate.R;
import com.orangepenguin.boilerplate.di.AndroidTestComponentFactory;
import com.orangepenguin.boilerplate.di.Injector;
import com.orhanobut.mockwebserverplus.MockWebServerPlus;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UsernameActivityTest {

    @JvmField
    @Rule
    public ActivityTestRule<UsernameActivity> activityRule = new ActivityTestRule<>(UsernameActivity.class, false,
            false);
    @JvmField @Rule public MockWebServerPlus server = new MockWebServerPlus();

    @Before
    public void setUp() throws Exception {

        Application context = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext()
                .getApplicationContext();
        Injector.setComponentFactory(new AndroidTestComponentFactory(context));
        String serverBaseUrl = server.url("/");
        Injector.getComponentFactory().getApplicationComponent().getGitHubUrl().set(serverBaseUrl);
        activityRule.launchActivity(null);
    }

    @Test
    public void dontDoNothing() {
        server.enqueue("github_user");

        onView(withId(R.id.username_edit_text))
                .perform(typeText("itsymbal"), closeSoftKeyboard());
        onView(withId(R.id.view_user_button)).perform(click());
        //        onView(withId(R.id.username_textview)).check(isDisplayed());
    }
}
