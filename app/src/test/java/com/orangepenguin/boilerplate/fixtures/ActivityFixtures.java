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
    private static Map<Class, ActivityController> CONTROLLER_MAP = new HashMap<>();

    private ActivityFixtures() {
    }

    public static <T extends Activity> T buildAndStartActivity(Class<T> activityClass) {
        ActivityController<T> controller = Robolectric.buildActivity(activityClass);

        T activity = controller.create().start().resume().get();
        CONTROLLER_MAP.put(activityClass, controller);
        return activity;
    }

    public static <T extends FragmentActivity> T emulateConfigurationChange(T oldActivity) {

        @SuppressWarnings("unchecked")
        ActivityController<T> oldController = CONTROLLER_MAP.get(oldActivity.getClass());

        Bundle state = new Bundle();
        Intent intent = oldActivity.getIntent();

        @SuppressWarnings("unchecked")
        Class<T> activityClass = (Class<T>) oldActivity.getClass();

        Object nonConfigurationInstance = oldActivity.onRetainCustomNonConfigurationInstance();
        oldController
                .saveInstanceState(state)
                .pause();

        ActivityController<T> newController = Robolectric.buildActivity(activityClass);
        CONTROLLER_MAP.put(activityClass, newController);

        T newActivity = newController.get();
        ShadowActivity newShadow = shadowOf(newActivity);
        newShadow.resetIsFinishing();

        Object instance = NonConfigurationInstanceHelper.createCustomInstances(nonConfigurationInstance);
        newShadow.setLastNonConfigurationInstance(instance);

        newController
                .withIntent(intent)
                .create(state)
                .postCreate(state);

        oldController
                .pause()
                .stop()
                .destroy();

        newController
                .start()
                .restoreInstanceState(state)
                .resume();
        return newActivity;
    }
}
