package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by filip on 6/10/15.
 */
public class ReflectMethodGetter implements PropertyGetter {

    private Method method;

    public ReflectMethodGetter(Method method) {
        this.method = method;
    }

    @Override
    public Object get(Object source) {
        try {
            return method.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class getValueType() {
        return method.getReturnType();
    }
}
