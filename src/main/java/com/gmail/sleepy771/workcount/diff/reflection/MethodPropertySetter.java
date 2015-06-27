package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 6/19/15.
 */
public class MethodPropertySetter extends AbstractPropertySetter {

    private Method method;

    protected MethodPropertySetter(Method method, Property property) {
        super(property, new MethodSignature(method));
        this.method = method;
    }


    // TODO inprove exceptions and exception handling
    @Override
    protected void setDirectly(Object source, Object value) throws Exception {
        method.invoke(source, value);
    }
}
