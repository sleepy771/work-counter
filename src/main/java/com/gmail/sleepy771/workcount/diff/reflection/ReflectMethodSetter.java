package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by filip on 6/10/15.
 */
public class ReflectMethodSetter implements PropertySetter, Classy {

    private final Method method;

    public ReflectMethodSetter(Method method) {
        this.method = method;
    }

    @Override
    public void set(Object destination, Object value) {
        if (!getValueType().isInstance(value))
            throw new IllegalArgumentException();
        if (!getForClass().isInstance(destination))
            throw new IllegalArgumentException();
        try {
            method.invoke(destination, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class getValueType() {
        return null;
    }

    @Override
    public Class getForClass() {
        return null;
    }
}
