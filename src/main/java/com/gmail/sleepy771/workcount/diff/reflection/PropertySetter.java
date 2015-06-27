package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;

/**
 * Created by filip on 6/19/15.
 */
public interface PropertySetter extends ForProperty {
    void set(Object destination, Object value) throws Exception;

    Class getSetterValueClass();

    boolean hasPresetValueHandler();

    ValueHandler getPresetValueHandler();

    Signature getSetterSignature();
}
