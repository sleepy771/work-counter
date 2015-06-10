package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.annotations.PatchProperty;
import com.gmail.sleepy771.workcount.diff.reflection.PropertyType;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

import java.lang.reflect.Method;
import java.util.EnumSet;

/**
 * Created by filip on 6/6/15.
 */
public class PropertyMethod {
    private final static EnumSet<PropertyType> AVAILABLE_TYPES = EnumSet.of(PropertyType.GETTER, PropertyType.SETTER);
    private final PropertyType type;
    private final Class propertyClass;
    private final String propertyName;
    private final MethodSignature methodMethodSignature;

    public PropertyMethod(PatchProperty patchProperty, Method method) {
        if (!AVAILABLE_TYPES.contains(patchProperty.type()))
            throw new IllegalArgumentException();
        this.type = patchProperty.type();
        this.propertyName = type.getPropertyName(patchProperty.name(), method.getName());
        this.propertyClass = type.getPropertyType(method);
        this.methodMethodSignature = new MethodSignature(method);
    }

    public MethodSignature getSignature() {
        return this.methodMethodSignature;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public PropertyType getType() {
        return this.type;
    }

    public Class getPropertyClass() {
        return this.propertyClass;
    }
}
