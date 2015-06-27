package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/19/15.
 */
public class FieldPropertySetter extends AbstractPropertySetter {

    private Field field;

    public FieldPropertySetter(Field field, Property property) {
        super(property, new FieldSignature(field));
        this.field = field;
    }

    @Override
    protected void setDirectly(Object source, Object value) throws Exception {
        this.field.set(source, value);
    }
}
