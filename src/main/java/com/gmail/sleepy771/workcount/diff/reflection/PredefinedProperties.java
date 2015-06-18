package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.patchers.ObjectPatcher;

import java.util.Collections;
import java.util.Map;

/**
 * Created by filip on 6/10/15.
 */
public class PredefinedProperties {

    private Map<Signature, Object> values;

    private Class forObject;

    public PredefinedProperties(Class forObject) {
        this.forObject = forObject;
    }

    public void set(Signature signature, Object value) {
        if (values.containsKey(signature))
            throw new IllegalArgumentException();
        if (!forObject.isAssignableFrom(signature.getForClass()))
            throw new IllegalArgumentException();
        if (!signature.getType().isInstance(value))
            throw new IllegalArgumentException();
        values.put(signature, value);
    }

    public Object get(Signature signature) {
        if (!values.containsKey(signature))
            throw new IllegalArgumentException("Undefined property");
        return values.get(signature);
    }

    public Map<Signature, Object> getValues() {
        return Collections.unmodifiableMap(values);
    }
}
