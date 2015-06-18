package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 6/7/15.
 */
public interface PropertySetter extends ForProperty {
    void set(Object destination, Object value) throws InvocationTargetException;

    Class getReceivedValueClass();

    Class getPropertyValueClass();
}
