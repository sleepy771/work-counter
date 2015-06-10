package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by filip on 6/10/15.
 */
public class CGMethodSetter implements PropertySetter<PredefinedProperties>, Classy {

    //TODO make implementation
    private Signature methodSignature;

    CGMethodSetter(Signature methodSignature) {
        this.methodSignature = methodSignature;
    }

    @Override
    public void set(PredefinedProperties destination, Object value) throws InvocationTargetException {
        destination.set(methodSignature, value);
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
