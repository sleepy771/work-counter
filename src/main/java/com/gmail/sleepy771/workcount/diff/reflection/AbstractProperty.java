package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/9/15.
 */
public abstract class AbstractProperty<T extends Signature> implements Property<T> {

    private final String name;
    private final Class type;
    private final PropertyAccessor accessor;
    private volatile int hashCode;

    protected AbstractProperty(String name, Class type, PropertyAccessor accessor) {
        this.accessor = accessor;
        this.name = name;
        this.type = type;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Class getClassType() {
        return type;
    }

    @Override
    public final PropertyAccessor getAccessor() {
        return accessor;
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            int hash = 17;
            hash = 31 * hash + name.hashCode();
            hash = 31 * hash + type.hashCode();
            hashCode = hash;
        }
        return hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == null || o.hashCode() != hashCode() || !AbstractProperty.class.isInstance(o)){
            return false;
        }
        AbstractProperty property = (AbstractProperty) o;
        return property.name.equals(this.name) && type == property.type;
    }
}
