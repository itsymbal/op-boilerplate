package com.orangepenguin.boilerplate.util;

/**
 * Represents an object which can fetch an image and provide it to a target object. The ImageFetcher
 * may apply transforms upon the image before providing it to the target.
 */
public interface ImageFetcher<S, T> {
    void fetchAndSetImage(S source, T target);
}
