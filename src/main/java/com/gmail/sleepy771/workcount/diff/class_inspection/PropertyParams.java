package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.DoNuttinHandler;
import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.annotations.Property;
import com.gmail.sleepy771.workcount.diff.annotations.PropertyType;
import com.gmail.sleepy771.workcount.diff.default_patches.MapPatch;
import com.gmail.sleepy771.workcount.diff.default_patches.MapPatch.Builder;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import javax.xml.bind.ValidationEvent;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 5/31/15.
 */
public class PropertyParams {

    public static class Builder {

        private String propertyName;

        private Map<PropertyType, Signature> signatureMap;

        private Map<PropertyType, Class<? extends ValueHandler>> handlers;

        private Property property;

        private ValueHandlerManager valueManager;

        public Builder(ValueHandlerManager valueManager, Property property, Method method) {
            this.valueManager = valueManager;
            this.property = property;
            this.propertyName = extractName(property, method);
            this.signatureMap = new HashMap<>();
            this.handlers = new HashMap<>();
            signatureMap.put(property.type(), new Signature(method));
            handlers.put(property.type(), property.valueHandlerClass());
        }

        public String getPropertyName() {
            return propertyName;
        }

        public Map<PropertyType, Signature> getSignatureMap() {
            return Collections.unmodifiableMap(signatureMap);
        }

        public PropertyParams.Builder bind(Property property, Method method) {
            if (signatureMap.containsKey(property.type())) {
                throw new IllegalArgumentException();
            }
            if (!extractName(property, method).equals(propertyName)) {
                throw new IllegalArgumentException();
            }
            signatureMap.put(property.type(), new Signature(method));
            return this;
        }

        public boolean isPrepared() {
            return signatureMap.containsKey(PropertyType.GETTER);
        }

        public static String extractName(Property property, Method method) {
            return property.type().getPropertyName(method.getName(), property.propertyName());
        }

        public PropertyParams build() throws InstantiationException, IllegalAccessException {
            if (!isPrepared())
                throw new IllegalArgumentException();
            boolean hasSetter = signatureMap.containsKey(PropertyType.SETTER);
            Class<? extends ValueHandler> getterHandlerClass = handlers.getOrDefault(PropertyType.GETTER, DoNuttinHandler.class);
            Class<? extends ValueHandler> setterHandlerClass = handlers.getOrDefault(PropertyType.SETTER, DoNuttinHandler.class);
            ValueHandler getterHandler = null;
            ValueHandler setterHandler = null;
            if (getterHandlerClass != null && !DoNuttinHandler.class.equals(getterHandlerClass)) {
                getterHandler = valueManager.getHandler(getterHandlerClass);
            }
            if (setterHandlerClass != null && !DoNuttinHandler.class.equals(setterHandlerClass)) {
                setterHandler = valueManager.getHandler(setterHandlerClass);
            }
            return new PropertyParams(propertyName, signatureMap.get(PropertyType.GETTER), signatureMap.get(PropertyType.SETTER), getterHandler, setterHandler);
        }
    }

    private PropertyParams(String propertyName, Signature getter, Signature setter, ValueHandler getterHandler, ValueHandler setterHandler) {

    }

    public String getPropertyName() {
        return null;
    }

    public Signature getGetter() {
        return null;
    }

    public Signature getSetter() {
        return null;
    }

    public boolean hasGetterHandler() {
        return getGetterHandler() != null;
    }

    public boolean hasSetterHandler() {
        return hasSetter() && getSetterHandler() != null;
    }

    public ValueHandler getGetterHandler() {
        return null;
    }

    public Class getPropertyType() {
        return Object.class;
    }

    public Class getPatchClass() {
        return Object.class;
    }

    @SuppressWarnings("unchecked")
    public <O, I> O prepareGetterValue(I value) {
        if (!hasGetterHandler())
            return (O) value;
        return ((ValueHandler<O, I>) getGetterHandler()).prepare(value);
    }

    @SuppressWarnings("unchecked")
    public <O, I> O prepareSetterValue(I value) {
        if (!hasSetterHandler()) {
            return (O) value;
        }
        return ((ValueHandler<O, I>) getSetterHandler()).prepare(value);
    }

    public ValueHandler getSetterHandler() {
        return null;
    }

    public boolean hasSetter() {
        return getSetter() != null;
    }


}
