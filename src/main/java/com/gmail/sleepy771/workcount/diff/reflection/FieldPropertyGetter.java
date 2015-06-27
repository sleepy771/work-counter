package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/19/15.
 */
public class FieldPropertyGetter extends AbstractPropertyGetter {

    private Field field;

    protected FieldPropertyGetter(Property property, Field field) {
        super(new FieldSignature(field), property);
    }

    @Override
    protected Object getDirectly(Object source) throws Exception {
        return field.get(source);
    }
}
