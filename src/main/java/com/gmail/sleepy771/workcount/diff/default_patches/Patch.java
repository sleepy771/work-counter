package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patch extends HasID {
    int getFromVersion();
    int getToVersion();
}
