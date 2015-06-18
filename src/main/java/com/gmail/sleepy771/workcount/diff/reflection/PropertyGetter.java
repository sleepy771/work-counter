package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 6/7/15.
 */
public interface PropertyGetter extends ForProperty {
    Object get(Object source) throws InvocationTargetException, IllegalAccessException;

    Class getPropertyValueClass();

    Class getOriginalValueClass();
}
