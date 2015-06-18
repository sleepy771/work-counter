package com.gmail.sleepy771.workcount.diff.reflection;

/**
 * Created by filip on 6/7/15.
 */
public interface PropertyAccessor extends PropertyGetter, PropertySetter {
    boolean isWriteAccessible();

    void setPropertyGetter(PropertyGetter getter);

    void setPropertySetter(PropertySetter setter);

    PropertyGetter getPropertyGetter();

    PropertySetter getPropertySetter();
}
