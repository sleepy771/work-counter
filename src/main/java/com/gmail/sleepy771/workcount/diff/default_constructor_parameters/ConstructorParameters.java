package com.gmail.sleepy771.workcount.diff.default_constructor_parameters;

import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 5/28/15.
 */
public interface ConstructorParameters extends Classy {
    Class[] parameterTypes();

    Object[] parameters();
}
