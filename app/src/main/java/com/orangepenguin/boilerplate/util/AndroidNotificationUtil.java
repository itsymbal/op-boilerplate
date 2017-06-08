package com.orangepenguin.boilerplate.util;

import android.content.Context;
import android.widget.Toast;

public class AndroidNotificationUtil implements NotificationUtil {

    private final Context context;

    public AndroidNotificationUtil(Context context) {
        this.context = context;
    }

    @Override
    public void showToast(String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
