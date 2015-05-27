package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.registers.Registrable;

/**
 * Created by filip on 5/25/15.
 */
public interface DefaultValuesHolder extends Classy, Registrable<Class> {
    Class[] getParameterTypes();

    Object[] getDefaultValues();
}
