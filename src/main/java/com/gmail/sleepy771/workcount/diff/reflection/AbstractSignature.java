package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/19/15.
 */
public abstract class AbstractSignature implements Signature {

    private final Class type, forClass;
    private final String name;
    private volatile int hashCode;

    protected AbstractSignature(String name, Class type, Class forClass) {
        this.name = name;
        this.type = type;
        this.forClass = forClass;
    }

    @Override
    public final Class getType() {
        return type;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Class getForClass() {
        return forClass;
    }

    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            int hash = 17;
            hash = 31 * hash + forClass.hashCode();
            hash = 31 * hash + name.hashCode();
            hash = 31 * hash + type.hashCode();
            hash = 31 * hash + implHash();
            hashCode = hash;
        }
        return hashCode;
    }

    protected abstract int implHash();

    protected abstract boolean implEquals(Object o);

    @Override
    public final boolean equals(Object o) {
        if (o == null)
            return false;
        if (o.hashCode() != hashCode() || !AbstractSignature.class.isInstance(o))
            return false;
        AbstractSignature sgn = (AbstractSignature) o;
        return this.name.equals(name) && forClass == sgn.forClass && type.isAssignableFrom(sgn.type)
                && implEquals(o);
    }
}
