package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/19/15.
 */
public class AbstractPropertyImpl implements Property {

    private final String name;
    private final Class type, forClass;
    private PropertyAccessor accessor;

    protected AbstractPropertyImpl(String name, Class type, Class forClass, PropertyAccessor accessor) {
        this.name = name;
        this.type = type;
        this.forClass = forClass;
        this.accessor = accessor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class getPropertyClass() {
        return type;
    }

    @Override
    public PropertyAccessor getAccessor() {
        return accessor;
    }

    @Override
    public Signature getPropertySignature() {
        return null;
    }

    @Override
    public boolean hasProperty(Object o) {
        return forClass.isInstance(o);
    }

    @Override
    public boolean isValidObject(Object o) {
        return false;
    }

    public void setAccessor(PropertyAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public Class getForClass() {
        return forClass;
    }
}
