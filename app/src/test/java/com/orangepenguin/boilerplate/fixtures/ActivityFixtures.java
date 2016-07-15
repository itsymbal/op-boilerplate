package com.orangepenguin.boilerplate.fixtures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NonConfigurationInstanceHelper;

import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import static org.robolectric.Shadows.shadowOf;

public class ActivityFixtures {
    private ActivityFixtures() {
    }

    private static Map<Class, ActivityController> CONTROLLER_MAP = new HashMap<>();

    public static <T extends Activity> T buildAndStartActivity(Class<T> activityClass) {
        ActivityController<T> controller = Robolectric.buildActivity(activityClass);

        T activity = controller.create().start().resume().get();
        CONTROLLER_MAP.put(activityClass, controller);
        return activity;
    }

    public static <T extends FragmentActivity> T simulateConfigurationChange(T activity) {

        ActivityController<T> oldController = CONTROLLER_MAP.get(activity.getClass());

        Bundle state = new Bundle();
        Intent intent = activity.getIntent();

        @SuppressWarnings("unchecked")
        Class<T> activityClass = (Class<T>) activity.getClass();

        Object nonConfigurationInstance = activity.onRetainCustomNonConfigurationInstance();
        oldController.saveInstanceState(state)
                     .pause()
                     .stop()
                     .destroy();


        ActivityController<T> newController = Robolectric.buildActivity(activityClass);
        CONTROLLER_MAP.put(activityClass, newController);

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
}
