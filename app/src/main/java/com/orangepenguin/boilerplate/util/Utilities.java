package com.orangepenguin.boilerplate.util;

import android.support.annotation.Nullable;

public class Utilities {
    /**
     * Copy of Android TextUtils method (because - get this - TextUtils is 'not mocked'. Bah!
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
