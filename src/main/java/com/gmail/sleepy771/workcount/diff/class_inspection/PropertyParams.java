package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.AsSelf;
import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.annotations.PatchProperty;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 5/31/15.
 */
public class PropertyParams {

    public static class Builder {
        private String propertyName;

        private Map<PropertyType, MethodSignature> signatureMap;

        private Map<PropertyType, ValueHandler> handlerMap;

        private Class patchAs;

        private Map<PropertyType, Class> type;

        public Builder(String propertyName) {
            this.propertyName = propertyName;
        }

        public Builder setValueHandler(PropertyType type, ValueHandler valueHandler) {
            if (this.signatureMap.containsKey(type))
                return null;
            return this;
        }

        public Builder addAccessor(PatchProperty patchProperty, Method method) {
            if (signatureMap.containsKey(patchProperty.type()))
                throw new IllegalArgumentException();
            if (this.patchAs != null) {
                if (patchProperty.patchAs() != AsSelf.class) {
                    if (this.patchAs != patchProperty.patchAs())
                        throw new IllegalArgumentException(); // Invalid Annotation
                } else {
                    if (this.patchAs != method.getReturnType()) {
                        throw new IllegalArgumentException(); //
                    }
                }
            } else {
                this.patchAs = method.getReturnType();
                if (patchProperty.patchAs() != AsSelf.class) {
                    this.patchAs = patchProperty.patchAs();
                }
            }
            return this;
        }
    }

    private PropertyParams(String propertyName, MethodSignature getter, MethodSignature setter, ValueHandler getterHandler, ValueHandler setterHandler) {

    }

    public String getPropertyName() {
        return null;
    }

    public MethodSignature getGetter() {
        return null;
    }

    public MethodSignature getSetter() {
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
