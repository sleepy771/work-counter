package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;

import java.util.Map;

/**
 * Created by filip on 6.5.2015.
 */
public class ClassInspectorManager implements Manager<ClassInspector> {

    private static ClassInspectorManager INSTANCE;

    private Map<Class, ClassInspector> inspectorMap;

    public ClassInspector inspect(Class clazz) {
        if (!inspectorMap.containsKey(clazz)) {
            register(new ClassInspector(clazz));

        }
        ClassInspector inspector = inspectorMap.get(clazz);
        inspector.runInspection();
        return inspector;
    }

    public void inspectAll() {
        for (ClassInspector inspector : inspectorMap.values()) {
            inspector.runInspection();
        }
    }

    private void recursiveInspection(Class clazz) {
        ClassInspector inspector = getOrCreateInspector(clazz);
        if (inspector.needsRecursiveInspection()) {
            for (Signature signature : inspector.getRecursivePropertyMap().keySet()) {
                recursiveInspection(signature.getReturnType());
            }
        }
    }

    public boolean isValid(Class clazz) {
        return getOrCreateInspector(clazz).isValid();
    }

    public boolean isValidRecursively(Class clazz) {
        return recursiveValidation(clazz);
    }

    private boolean recursiveValidation(Class clazz) {
        boolean isValid = getOrCreateInspector(clazz).isValid();
        for (Signature entry : getOrCreateInspector(clazz).getRecursivePropertyMap().keySet()) {
            if (!isValid)
                return false;
            isValid = recursiveValidation(entry.getReturnType());
        }
        return isValid;
    }

    private ClassInspector getOrCreateInspector(Class clazz) {
        if (!inspectorMap.containsKey(clazz)) {
            register(new ClassInspector(clazz));
        }
        return inspectorMap.get(clazz);
    }

    @Override
    public void register(ClassInspector element) {
        if (inspectorMap.containsKey(element.getInspectedClass()))
            throw new IllegalArgumentException("Inspector for class " + element.getInspectedClass().getName() + ", already registered");
        inspectorMap.put(element.getInspectedClass(), element);
    }

    @Override
    public void unregister(ClassInspector element) {
        inspectorMap.remove(element.getInspectedClass(), element);
    }

    @Override
    public boolean isRegistered(ClassInspector element) {
        return inspectorMap.containsKey(element.getInspectedClass()) && inspectorMap.get(element.getInspectedClass()).equals(element);
    }

    public static ClassInspectorManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClassInspectorManager();
        }
        return INSTANCE;
    }
}
