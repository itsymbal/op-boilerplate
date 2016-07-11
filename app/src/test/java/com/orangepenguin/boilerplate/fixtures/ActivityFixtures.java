package com.orangepenguin.boilerplate.fixtures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NonConfigurationInstanceHelper;

import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.robolectric.Shadows.shadowOf;

public class ActivityFixtures {

    private ActivityFixtures() {
    }

    public static <T extends Activity> T buildAndStartActivity(Class<T> activityClass) {
        T activity = Robolectric.buildActivity(activityClass).create().start().resume().get();
        return activity;
    }

    public static <T extends Activity> T startActivity(T activity) {
        ActivityController controller = ActivityController.of(Robolectric.getShadowsAdapter(),
                activity);
        controller.create().start().resume().get();
        return activity;
    }

    public static <T extends FragmentActivity> T buildActivity(Class<T> activityClass) {
        return Robolectric.buildActivity(activityClass).get();
    }

    public static <T extends FragmentActivity> T simulateConfigurationChange(ActivityController<T>
                                                                                     oldController) {
        Bundle state = new Bundle();
        T activity = oldController.get();
        Intent intent = activity.getIntent();

        @SuppressWarnings("unchecked")
        Class<T> activityClass = (Class<T>) activity.getClass();

        Object nonConfigurationInstance = activity.onRetainCustomNonConfigurationInstance();
        oldController.saveInstanceState(state)
                .pause()
                .stop()
                .destroy();


        ActivityController<T> newController = Robolectric.buildActivity(activityClass);
        T newActivity = newController.get();
        ShadowActivity newShadow = shadowOf(newActivity);

        Object instance = NonConfigurationInstanceHelper.createCustomInstances
                (nonConfigurationInstance);
        newShadow.setLastNonConfigurationInstance(instance);

        newController
                .withIntent(intent)
                .create(state)
                .postCreate(state)
                .start()
                .restoreInstanceState(state)
                .resume()
                .get();
        return newActivity;
    }

    public static <T extends FragmentActivity> T simulateConfigurationChange(T activity) {
        T newActivity = simulateConfigurationChange(ActivityController.of(Robolectric
                .getShadowsAdapter(), activity));
        return newActivity;
    }
}
