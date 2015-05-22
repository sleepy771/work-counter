package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patch extends HasID {
    int getFromVersion();
    int getToVersion();

    // TODO this should return only delta
    Patch getDeltaFor(Signature signature) throws IllegalArgumentException;
}
