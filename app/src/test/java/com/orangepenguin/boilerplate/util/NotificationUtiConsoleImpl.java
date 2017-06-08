package com.orangepenguin.boilerplate.util;

public class NotificationUtiConsoleImpl implements NotificationUtil {
    @Override
    public void showToast(String message, int duration) {
        System.out.println(message);
    }
}
