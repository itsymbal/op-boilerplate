package com.squareup.picasso;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MockPicasso extends Picasso {
    private static ArrayList<String> paths;
    private static ArrayList<ImageView> views;

    MockPicasso() {
        super(null, null, null, null, null, null, new MockStats(), false, false);
    }

    /** Initializes new {@code MockPicasso} and replaces production instance. */
    public static void init() {
        paths = new ArrayList<>();
        views = new ArrayList<>();
        singleton = new MockPicasso();
    }

    /** Returns a list of all image paths in the order requested. */
    public static List getImagePaths() {
        return paths;
    }

    /** Returns the most recent image path requested. */
    public static String getLastImagePath() {
        return paths.size() > 0 ? paths.get(paths.size() - 1) : null;
    }

    /** Returns a list of all {@link ImageView} targets in the order requested. */
    public static List getTargetImageViews() {
        return views;
    }

    /** Returns the most recent {@link ImageView} target. */
    public static ImageView getLastTargetImageView() {
        return views.size() > 0 ? views.get(views.size() - 1) : null;
    }

    @Override
    public RequestCreator load(String path) {
        paths.add(path);
        return new MockRequestCreator();
    }

    static class MockRequestCreator extends RequestCreator {
        @Override
        public void into(ImageView target) {
            views.add(target);
        }
    }

    static class MockStats extends Stats {
        MockStats() {
            super(Cache.NONE);
        }
    }
}