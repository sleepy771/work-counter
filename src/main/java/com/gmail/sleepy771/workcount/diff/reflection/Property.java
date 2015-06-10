package com.gmail.sleepy771.workcount.diff.reflection;


/**
 * Created by filip on 6/6/15.
 */
public interface Property<T extends Signature> {
    String getName();

    // Maybe this is useless
    PropertyType getPropertyType();

    Class getClassType();

    PropertyAccessor getAccessor();

    T getSignature();
}
