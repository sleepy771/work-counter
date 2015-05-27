package com.gmail.sleepy771.workcount.diff.object_construction;

/**
 * Created by filip on 5/25/15.
 */
public class XMLValuesHolder implements DefaultValuesHolder {

    private final Class[] parameterTypes;
    private final Object[] defaultValues;
    private final Class forClass;

    public XMLValuesHolder(Class forClass, Class[] parameterTypes, Object[] defaultValues) {
        this.forClass = forClass;
        this.parameterTypes = parameterTypes;
        this.defaultValues = defaultValues;
    }

    @Override
    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getDefaultValues() {
        return defaultValues;
    }

    @Override
    public Class getForClass() {
        return forClass;
    }
}
