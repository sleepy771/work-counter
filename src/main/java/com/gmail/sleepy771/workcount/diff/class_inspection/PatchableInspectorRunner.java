package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.annotations.*;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.exceptions.IdException;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by filip on 5/24/15.
 */
public class PatchableInspectorRunner {

    private final Class<? extends Patchable> patchableType;

    private final Class classForPatch;

    private boolean hasZeroArgumentConstructor = false;

    private boolean needsInjection = false;

    private ConstructionLogic constructionLogick;

    public PatchableInspectorRunner(Class clazz) {
        CanPatch canPatch = (CanPatch) clazz.getAnnotation(CanPatch.class);
        if (canPatch == null)
            throw new IllegalArgumentException("Wrong class type");
        patchableType = canPatch.asPatchable();
        classForPatch = clazz;
    }

    public void inspectConstructors() {
        for (Constructor constructor : classForPatch.getConstructors()) {
            hasZeroArgumentConstructor |= constructor.getParameterCount() == 0;
            if (constructor.getAnnotation(InjectConstructorArguments.class) != null)
                needsInjection = true;
        }
    }

    public void inspectMethods() {
        Map<Signature, PropertyOptions> optionsMap = new HashMap<>();
        Map<String, SetterBinder.Builder> builderMap = new HashMap<>();
        for (Method method : classForPatch.getMethods()) {
            Property property = method.getAnnotation(Property.class);
            if (property != null && property.type() == PropertyType.GETTER) {
                PropertyOptions options = new PropertyOptions(method, property);
                optionsMap.put(new Signature(method), options);
                if (builderMap.containsKey(options.getPropertyName())) {
                    builderMap.get(options.getPropertyName()).bindTo(options);
                }
            }
        }
        Set<SetterBinder> bounds = builderMap.values().stream().filter(SetterBinder.Builder::isBuildable).map(SetterBinder.Builder::build).collect(Collectors.toSet());
    }

    public void searchAccessibleID() throws IdException {
        Method idMethod = null;
        Field idField = null;
        for (Method method : classForPatch.getMethods()) {
            if (method.getAnnotation(ID.class) != null && isAccessibleIDMethod(method)) {
                if (idMethod != null)
                    throw new IdException("Multiple id's found");
                idMethod = method;
            }
        }
        for (Field field : classForPatch.getFields()) {
            if (field.getAnnotation(ID.class) != null  && isAccessibleIDField(field)) {
                if (idMethod != null || idField != null)
                    throw new IdException("Multiple id's found");
                idField = field;
            }
        }

        if (idMethod == null && idField == null)
            throw new IdException("No Id was found");
    }

    public boolean isAccessibleIDMethod(Method method) {
        return !(Modifier.isFinal(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) && (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers()));
    }

    public boolean isAccessibleIDField(Field field) {
        return !(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) && Modifier.isPublic(field.getModifiers());
    }
}
