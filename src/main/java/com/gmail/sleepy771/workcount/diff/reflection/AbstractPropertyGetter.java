package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.exceptions.InvalidValueHandlerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PropertyGetterException;

import java.lang.ref.WeakReference;

/**
 * Created by filip on 6/19/15.
 */
public abstract class AbstractPropertyGetter implements PropertyGetter {

    private ValueHandler getterValueHandler;
    private final Signature getterSignature;
    private WeakReference<Property> propertyWeakReference;

    protected AbstractPropertyGetter(Signature getterSignature, Property property) {
        this.getterSignature = getterSignature;
        this.propertyWeakReference = new WeakReference<Property>(property);
    }

    @Override
    public Object get(Object source) throws PropertyGetterException {
        if (needsHandler() && !hasPostGetValueHandler())
            throw new PropertyGetterException("Property needs ValueHandler but it was not assigned");
        try {
            return getterValueHandler.prepare(getDirectly(source));
        } catch (Exception e) {
            throw new PropertyGetterException("Sumething failed"); // TODO split exceptions
        }
    }

    protected final boolean needsHandler() {
        return propertyWeakReference.get() != null
                && !propertyWeakReference.get().getPropertySignature().getType().isAssignableFrom(getGetterSignature().getType());
    }

    protected abstract Object getDirectly(Object source) throws Exception;

    @Override
    public final boolean hasPostGetValueHandler() {
        return getterValueHandler != null;
    }

    @Override
    public final ValueHandler getPostGetValueHandler() {
        return getterValueHandler;
    }

    public final void setPostGetValueHandler(ValueHandler handler) throws InvalidValueHandlerException {
        if (handler == null && !getProperty().getPropertySignature().getType().isAssignableFrom(getGetterSignature().getType()))
            throw new NullPointerException("Handler can not be dereferenced");
        if (!getProperty().getPropertySignature().getType().isAssignableFrom(handler.getOutputClass()))
            throw new InvalidValueHandlerException("Class " + getGetterSignature().getType().getName() + " is not assignable from class "+ handler.getOutputClass());
        this.getterValueHandler = handler;
    }

    @Override
    public final Signature getGetterSignature() {
        return getterSignature;
    }

    @Override
    public final Property getProperty() {
        return propertyWeakReference.get();
    }
}
