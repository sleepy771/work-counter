package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/9/15.
 */
public class FieldSignature implements Signature {

    private volatile int hash;
    private final Class type;
    private final String name;
    private final Class inClass;

    public FieldSignature(String name, Class type, Class forClass) {
        this.type = type;
        this.name = name;
        this.inClass = forClass;
    }

    public FieldSignature(Field field) {
        this(field.getName(), field.getType(), field.getDeclaringClass());
    }


    public Class getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public Class getForClass() {
        return inClass;
    }

    public Field getField() throws NoSuchFieldException {
        return getForClass().getField(getName());
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            int hashCode = 17;
            hashCode = hashCode * 31 + name.hashCode();
            hashCode = hashCode * 31 + inClass.hashCode();
            hashCode = hashCode * 31 + type.hashCode();
            hash = hashCode;
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !FieldSignature.class.isInstance(o) || o.hashCode() != hashCode())
            return false;
        FieldSignature sgn = (FieldSignature) o;
        return sgn.inClass == this.inClass && sgn.name.equals(this.name) && sgn.type == this.type;
    }
}
