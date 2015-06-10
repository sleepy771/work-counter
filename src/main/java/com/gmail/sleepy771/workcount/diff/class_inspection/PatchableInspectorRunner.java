package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.AsSelf;
import com.gmail.sleepy771.workcount.diff.DoNuttinHandler;
import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.annotations.InjectConstructorArguments;
import com.gmail.sleepy771.workcount.diff.annotations.PatchProperty;
import com.gmail.sleepy771.workcount.diff.reflection.PropertyType;
import com.gmail.sleepy771.workcount.diff.class_inspection.PropertyParams.Builder;
import com.gmail.sleepy771.workcount.diff.exceptions.FieldPropertyException;
import com.gmail.sleepy771.workcount.diff.exceptions.InvalidPatchableClassException;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 5/24/15.
 */
public class PatchableInspectorRunner implements PatchableInspector {

    private final Class clazz;

    private boolean inspected = false;

    private ValueHandlerManager valueManager;

    private Map<String, PropertyParams> properties;

    private Map<String, FieldProperty> fieldPropertyMap;

    private Constructor constructor;

    private InjectConstructorArguments args;

    private boolean hasArgumentLessConstructor;

    public PatchableInspectorRunner(Class clazz) {
        this.clazz = clazz;
    }


    @Override
    public void runInspection() {
        if (isInspected())
            return;

        inspected = true;
    }

    @Override
    public boolean isInspected() {
        return inspected;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Scheme getScheme() {
        return null;
    }

    @Override
    public Class getForClass() {
        return this.clazz;
    }

    private void inspectProperties() throws IllegalAccessException, InstantiationException {
        Map<String, PropertyParams.Builder> paramsBuilders = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            PatchProperty patchProperty = method.getAnnotation(PatchProperty.class);
            if (patchProperty != null) {
                String propertyName = patchProperty.type().getPropertyName(method.getName(), patchProperty.name());
                if (paramsBuilders.containsKey(propertyName)) {
                    paramsBuilders.get(propertyName).bind(patchProperty, method);
                } else {
                    paramsBuilders.put(propertyName, new Builder(valueManager, patchProperty, method));
                }
            }
        }
        Map<String, PropertyParams> paramsMap = new HashMap<>();
        for (PropertyParams.Builder builder : paramsBuilders.values()) {
            PropertyParams params = builder.build();
            paramsMap.put(params.getPropertyName(), params);
        }
        properties = Collections.unmodifiableMap(paramsMap);
    }

    private void inspectConstructors() throws InvalidPatchableClassException {
        constructor = null;
        this.args = null;
        hasArgumentLessConstructor = false;
        for (Constructor constructor : clazz.getConstructors()) {
            InjectConstructorArguments injectConstructorArguments = (InjectConstructorArguments) constructor.getAnnotation(InjectConstructorArguments.class);
            if (injectConstructorArguments != null) {
                if (args == null && this.constructor == null) {
                    args = injectConstructorArguments;
                    this.constructor = constructor;
                } else {
                    throw new InvalidPatchableClassException(clazz, "Too many annotations");
                }
                if (constructor.getParameterCount() == 0) {
                    hasArgumentLessConstructor = true;
                    if (args == null) {
                        this.constructor = constructor;
                    }
                }
            }
        }
    }

    private void inspectFields() throws FieldPropertyException {
        Map<String, FieldProperty> fieldPropertyMap = new HashMap<>();
        for (Field field : clazz.getFields()) {
            PatchProperty patchProperty = field.getAnnotation(PatchProperty.class);
            if (patchProperty != null) {
                if (patchProperty.type() != PropertyType.FIELD)
                    throw new FieldPropertyException(field.getName(), "Invalid patchProperty type: " + patchProperty.type() + ", but " + PropertyType.FIELD + " was expected!");
                if (!Modifier.isPublic(field.getModifiers()))
                    throw new FieldPropertyException(patchProperty.type().getPropertyName(field.getName(), patchProperty.name()), "Field is not Public");
                if (patchProperty.patchAs() == AsSelf.class) {
                    String propertyName = patchProperty.type().getPropertyName(field.getName(), patchProperty.name());
                    fieldPropertyMap.put(propertyName, new FieldProperty(propertyName, field.getType()));
                } else {
                    String propertyName = patchProperty.type().getPropertyName(field.getName(), patchProperty.name());
                    ValueHandler toTypeHandler = null;
                    ValueHandler fromTypeHandler = null;
                    try {
                        if (patchProperty.fromTypeHandlerClass() != DoNuttinHandler.class) {
                            fromTypeHandler = valueManager.getHandler(patchProperty.fromTypeHandlerClass());
                        }
                        if (patchProperty.toTypeHandlerClass() != DoNuttinHandler.class) {
                            toTypeHandler = valueManager.getHandler(patchProperty.toTypeHandlerClass());
                        }
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new FieldPropertyException(propertyName, e);
                    }
                    fieldPropertyMap.put(propertyName,
                            new FieldProperty(propertyName, field.getType(), patchProperty.patchAs(), fromTypeHandler, toTypeHandler));
                }
            }
        }
        this.fieldPropertyMap = Collections.unmodifiableMap(fieldPropertyMap);
    }

    private void inspectClass(Class clazz) {
        // TODO this will add class to InspectionQueue
    }
}
