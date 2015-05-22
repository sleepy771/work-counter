package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 18.4.2015.
 */
public interface Patchable extends HasID, Classy {
    int getVersion();

    Object exclude();
}
