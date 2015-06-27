package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;

import java.lang.ref.WeakReference;

/**
 * Created by filip on 6/19/15.
 */
public abstract class AbstractPropertySetter implements PropertySetter {

    private Signature setterSignature;
    private ValueHandler handler;
    private WeakReference<Property> propertyWeakReference;

    protected AbstractPropertySetter(Property property, Signature setterSignature) {
        this.setterSignature = setterSignature;
        this.propertyWeakReference = new WeakReference<>(property);
    }

    @Override
    public void set(Object destination, Object value) throws Exception {
        // TODO create better exceptions
        if (!propertyWeakReference.get().isValidObject(destination)) {
            throw new IllegalArgumentException();
        }
        if (!isValidValueType(value))
            throw new IllegalArgumentException();
        setDirectly(destination, value);
    }

    protected abstract void setDirectly(Object source, Object value) throws Exception;

    private boolean isValidValueType(Object value) {
        return true;
    }

    @Override
    public Class getSetterValueClass() {
        return propertyWeakReference.get().getPropertyClass();
    }

    @Override
    public boolean hasPresetValueHandler() {
        return handler != null;
    }

    @Override
    public ValueHandler getPresetValueHandler() {
        return handler;
    }

    public void setPresetValueHandler(ValueHandler handler) {
        if (handler == null && needsValueHandler())
            throw new IllegalArgumentException();
        if (!propertyWeakReference.get().getPropertySignature().getType().isAssignableFrom(handler.getInputClass())) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    protected boolean needsValueHandler() {
        return getProperty().getPropertySignature().getType().isAssignableFrom(setterSignature.getType());
    }

    @Override
    public Signature getSetterSignature() {
        return setterSignature;
    }

    @Override
    public Property getProperty() {
        return propertyWeakReference.get();
    }
}
