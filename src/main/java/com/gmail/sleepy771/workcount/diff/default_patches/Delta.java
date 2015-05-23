package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 5/22/15.
 */
public interface Delta<T> extends Classy {
    T getPatch();
}
