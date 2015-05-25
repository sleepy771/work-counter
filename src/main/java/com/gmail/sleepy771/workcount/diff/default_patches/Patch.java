package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.util.Map;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patch extends HasID, Iterable<Map.Entry<Signature, Delta>>, Classy, Delta {
    int getFromVersion();
    int getToVersion();

    // TODO this should return only delta
    Delta getDeltaFor(Signature signature) throws IllegalArgumentException;

    boolean hasDeltaFor(Signature signature);

    boolean isPatch(Signature signature);
}
