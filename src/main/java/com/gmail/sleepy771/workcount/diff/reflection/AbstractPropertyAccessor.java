package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.exceptions.PropertyGetterException;

import java.lang.ref.WeakReference;

/**
 * Created by filip on 6/19/15.
 */
public class AbstractPropertyAccessor implements PropertyAccessor {


    private WeakReference<Property> propertyWeakReference;
    private PropertySetter setter;
    private PropertyGetter getter;

    protected AbstractPropertyAccessor(Property property, PropertyGetter getter, PropertySetter setter) {
        propertyWeakReference = new WeakReference<Property>(property);
        this.getter = getter;
        this.setter = setter;
    }

    public void setSetter(PropertySetter setter) {


        this.setter = setter;
    }

    public void setGetter(PropertyGetter getter) {



        this.getter = getter;
    }

    @Override
    public PropertyGetter getGetter() {
        return getter;
    }

    @Override
    public PropertySetter getSetter() {
        return setter;
    }

    @Override
    public Object get(Object source) throws PropertyGetterException {
        return getGetter().get(source);
    }

    @Override
    public boolean hasPostGetValueHandler() {
        return getGetter().hasPostGetValueHandler();
    }

    @Override
    public ValueHandler getPostGetValueHandler() {
        return getGetter().getPostGetValueHandler();
    }

    @Override
    public Signature getGetterSignature() {
        return getGetter().getGetterSignature();
    }

    @Override
    public void set(Object destination, Object value) throws Exception {
        getSetter().set(destination, value);
    }

    @Override
    public Class getSetterValueClass() {
        return getSetter().getSetterValueClass();
    }

    @Override
    public boolean hasPresetValueHandler() {
        return getSetter().hasPresetValueHandler();
    }

    @Override
    public ValueHandler getPresetValueHandler() {
        return getSetter().getPresetValueHandler();
    }

    @Override
    public Signature getSetterSignature() {
        return getSetter().getSetterSignature();
    }

    @Override
    public Property getProperty() {
        return propertyWeakReference.get();
    }
}
