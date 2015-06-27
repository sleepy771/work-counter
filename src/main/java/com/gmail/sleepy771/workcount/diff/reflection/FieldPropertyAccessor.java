package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/19/15.
 */
public class FieldPropertyAccessor extends AbstractPropertyAccessor {

    public FieldPropertyAccessor(Field field, Property property) {
        super(property, new FieldPropertyGetter(property, field), new FieldPropertySetter(field, property));
    }
}
