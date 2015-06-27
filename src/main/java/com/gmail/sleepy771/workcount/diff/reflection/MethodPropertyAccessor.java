package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 6/19/15.
 */
public class MethodPropertyAccessor extends AbstractPropertyAccessor {
    public MethodPropertyAccessor(Method getter, Method setter, Property property) {
        super(property, new MethodPropertyGetter(property, getter), new MethodPropertySetter(setter, property));
    }
}
