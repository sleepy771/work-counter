package com.gmail.sleepy771.workcount.diff.reflection;

public class MethodProperty extends AbstractProperty<MethodSignature> {

    private final MethodSignature signature;
    private final PropertyType propertyType;

    private MethodProperty(String name, Class type, PropertyAccessor accessor, MethodSignature signature, PropertyType propertyType) {
        super(name, type, accessor);
        this.signature = signature;
        this.propertyType = propertyType;
    }

    @Override
    public PropertyType getPropertyType() {
        return propertyType;
    }

    @Override
    public MethodSignature getSignature() {
        return signature;
    }
}
