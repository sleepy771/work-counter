package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Identifiable;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patch<T> extends Identifiable {
    int getFromVersion();
    int getToVersion();
}
