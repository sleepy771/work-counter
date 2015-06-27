package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/19/15.
 */
public interface Property extends Classy {
    String getName();

    Class getPropertyClass();

    PropertyAccessor getAccessor();

    Signature getPropertySignature();

    boolean hasProperty(Object o);

    boolean isValidObject(Object o);
}
