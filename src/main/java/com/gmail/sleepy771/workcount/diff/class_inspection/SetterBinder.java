package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.annotations.PropertyType;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 5/25/15.
 */
public class SetterBinder {

    public static class Builder {
        private Map<PropertyType, PropertyOptions> propertyMap;
        private String propertyName;

        public Builder(Method method) {
            this(new PropertyOptions(method));
        }

        public Builder(PropertyOptions options) {
            propertyMap = new HashMap<>();
            set(options);
            propertyName = options.getPropertyName();
        }

        private void set(PropertyOptions options) {
            if (propertyMap.containsKey(options.getType()))
                throw new IllegalArgumentException("Property type: " + options.getType() + " already registered");
            if (!isForProperty(options.getPropertyName()))
                throw new IllegalArgumentException("Invalid property name");
            if (!propertyMap.isEmpty() && propertyMap.size() > 1) {
                throw new UnsupportedOperationException();
            }
            propertyMap.put(options.getType(), options);
        }

        public Builder bindTo(PropertyOptions options) {
            set(options);
            return this;
        }

        public Builder bindTo(Method method) {
            bindTo(new PropertyOptions(method));
            return this;
        }

        public SetterBinder build() {
            if (!(hasSetter() && hasGetter()))
                throw new IllegalArgumentException("Bound not specified");
            return new SetterBinder(propertyMap.get(PropertyType.SETTER).getSignature(), propertyMap.get(PropertyType.GETTER).getSignature(), propertyName);
        }

        public boolean isForProperty(String propertyName) {
            return this.propertyName.equals(propertyName);
        }

        public boolean hasSetter() {
            return propertyMap.containsKey(PropertyType.SETTER);
        }

        public boolean hasGetter() {
            return propertyMap.containsKey(PropertyType.GETTER);
        }

        public PropertyOptions getGetter() {
            if (!hasGetter())
                throw new UnsupportedOperationException();
            return propertyMap.get(PropertyType.GETTER);
        }

        public PropertyOptions getSetter() {
            if (!hasSetter())
                throw new UnsupportedOperationException();
            return propertyMap.get(PropertyType.GETTER);
        }

        public String getPropertyName() {
            return propertyName;
        }

        public boolean isBuildable() {
            return hasGetter() && hasSetter();
        }
    }

    private final Signature getterSignature;

    private final Signature setterSignature;

    private final String propertyName;

    private SetterBinder(Signature setterSignature, Signature getterSignature, String propertyName) {
        this.setterSignature = setterSignature;
        this.getterSignature = getterSignature;
        this.propertyName = propertyName;
    }

    public Signature getGetterSignature() {
        return getterSignature;
    }

    public Signature getSetterSignature() {
        return setterSignature;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
