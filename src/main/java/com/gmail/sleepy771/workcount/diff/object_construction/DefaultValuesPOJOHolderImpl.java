package com.gmail.sleepy771.workcount.diff.object_construction;

/**
 * Created by filip on 5/27/15.
 */
public class DefaultValuesPOJOHolderImpl implements DefaultValuesHolder {

    private Class forClass;

    private Class[] parameterTypes;

    private  Object[] defaultValues;

    public void setForClass(Class clazz) {
        this.forClass = clazz;
    }

    public void setParameterTypes(Class[] classes) {
        this.parameterTypes = classes;
    }

    public void setDefaultValues(Object[] values) {
        this.defaultValues = values;
    }

    @Override
    public Class[] getParameterTypes() {
        Class[] clone = new Class[parameterTypes.length];
        System.arraycopy(parameterTypes, 0, clone, 0, parameterTypes.length);
        return clone;
    }

    @Override
    public Object[] getDefaultValues() {
        Object[] clone = new Object[defaultValues.length];
        System.arraycopy(defaultValues, 0, clone, 0, defaultValues.length);
        return clone;
    }

    @Override
    public Class getForClass() {
        return this.forClass;
    }
}
