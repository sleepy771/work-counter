package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 5/25/15.
 */
public interface DefaultValuesHolder extends Classy {
    Class[] getParameterTypes();

    Object[] getDefaultValues();
}
