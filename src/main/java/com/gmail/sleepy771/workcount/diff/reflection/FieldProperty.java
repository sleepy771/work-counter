package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/7/15.
 */
public class FieldProperty extends AbstractProperty<FieldSignature> {

    private final FieldSignature signature;

    private FieldProperty(String propertyName, Class type, PropertyAccessor accessor, FieldSignature signature) {
        super(propertyName, type, accessor);
        this.signature = signature;
    }

    @Override
    public PropertyType getPropertyType() {
        return PropertyType.FIELD;
    }

    @Override
    public FieldSignature getSignature() {
        return signature;
    }
}
