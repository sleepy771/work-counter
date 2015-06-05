package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.exceptions.FieldPropertyException;

import java.lang.reflect.Field;

/**
 * Created by filip on 6/4/15.
 */
public class FieldProperty {

    private final String propertyName;
    private final Class type;
    private final Class patchAs;
    private final ValueHandler fromField, toField;
    private final boolean needsCast;

    public FieldProperty(String propertyName, Class type, Class patchAs, ValueHandler fromField, ValueHandler toField) throws FieldPropertyException {
        if (propertyName == null || "-".equals(propertyName) || "".equals(propertyName)) {
            throw new FieldPropertyException((propertyName == null) ? "[UNKNOWN]" : propertyName, "Invalid property name");
        }
        if (type == null || patchAs == null) {
            throw new FieldPropertyException(propertyName, "type or patch type is null");
        }
        needsCast = !type.isAssignableFrom(patchAs);
        if ((fromField == null || toField == null) && needsCast) {
            throw new FieldPropertyException(propertyName, "At least one ValueHandler was not assigned, but " + patchAs.getName() + " is not supper class of " + type.getName());
        }
        this.propertyName = propertyName;
        this.type = type;
        this.patchAs = patchAs;
        this.fromField = fromField;
        this.toField = toField;
    }

    public FieldProperty(String propertyName, Class type) throws FieldPropertyException {
        this(propertyName, type, type, null, null);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class getType() {
        return type;
    }

    public Class getPatchAs() {
        return patchAs;
    }

    public boolean needsTypeCast() {
        return needsCast;
    }

    public boolean isTypeCastAvailable() {
        return fromField != null || toField != null;
    }

    public boolean hasFromFieldHandler() {
        return fromField != null;
    }

    public boolean hasToFieldHandler() {
        return toField != null;
    }

    public ValueHandler getFromFieldHandler() {
        return fromField;
    }

    public ValueHandler getToFieldHandler() {
        return toField;
    }
}
