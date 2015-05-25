package com.gmail.sleepy771.workcount.diff.class_inspection;


import com.gmail.sleepy771.workcount.diff.PreProcessHandler;
import com.gmail.sleepy771.workcount.diff.annotations.Property;
import com.gmail.sleepy771.workcount.diff.annotations.PropertyType;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by filip on 5/25/15.
 */
public class PropertyOptions {

    private final Signature signature;

    private final Method method;

    private final Property property;

    private final String propertyName;

    public PropertyOptions(Method propertyMethod) {
        this(propertyMethod, propertyMethod.getAnnotation(Property.class));
    }

    public PropertyOptions(Method method, Property property) {
        this(method, property, new Signature(method));
    }

    private PropertyOptions(Method method, Property property, Signature signature) {
        if (property == null)
            throw new NullPointerException("Property can not be null");
        if (method.getParameterCount() != 0)
            throw new IllegalArgumentException("Method is not valid property with 0 parameter count");
        this.signature = signature;
        this.property = property;
        this.method = method;
        this.propertyName = property.type().getPropertyName(method.getName(), property.propertyName());
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Property getProperty() {
        return property;
    }

    public Signature getSignature() {
        return signature;
    }

    public PropertyType getType() {
        return property.type();
    }

    public boolean needsInspection() {
        return property.needsInspection();
    }

    public Class getPreProcessingClass() {
        return property.preProcessingClass();
    }

    public Class getPatchableClass() {
        return property.patchAs();
    }

    public boolean isGetter() {
        return property.type() == PropertyType.GETTER;
    }

    public boolean isSetter() {
        return property.type() == PropertyType.SETTER;
    }

    public void invokeSet(Object patchedObject, Object value) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (!isSetter())
            throw new UnsupportedOperationException("This operation is not supported for non setter methods");
        PreProcessHandler handler = (PreProcessHandler) getPatchableClass().newInstance();
        method.invoke(patchedObject, handler.prepare(value));
    }

    public <T> T invokeGet(Object originalObject) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (!isGetter())
            throw new UnsupportedOperationException("This operation is not supported for non getter methods");
        PreProcessHandler handler = (PreProcessHandler) getPatchableClass().newInstance();
        return (T) method.invoke(originalObject);
    }

    @Override
    public int hashCode() {
        return signature.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || !PropertyOptions.class.isInstance(o)) {
            return false;
        }
        PropertyOptions handler = (PropertyOptions) o;
        return handler.signature.equals(this.signature);
    }
}
