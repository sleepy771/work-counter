package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.exceptions.PropertyGetterException;

/**
 * Created by filip on 6/19/15.
 */
public interface PropertyGetter extends ForProperty {
    Object get(Object source) throws PropertyGetterException;

    boolean hasPostGetValueHandler();

    ValueHandler getPostGetValueHandler();

    Signature getGetterSignature();
}
