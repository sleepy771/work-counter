package com.gmail.sleepy771.workcount.diff.reflection;


/**
 * Created by filip on 6/19/15.
 */
public interface PropertyAccessor extends PropertyGetter, PropertySetter, ForProperty {

    PropertyGetter getGetter();

    PropertySetter getSetter();
}
