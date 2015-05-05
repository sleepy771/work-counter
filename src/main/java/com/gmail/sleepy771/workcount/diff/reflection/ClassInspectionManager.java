package com.gmail.sleepy771.workcount.diff.reflection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 3.5.2015.
 */
public class ClassInspectionManager {

    public static final ClassInspectionManager INSTANCE = new ClassInspectionManager();

    private Map<Class, ClassInspector> inspectorMap = new HashMap<>();

    private ClassInspectionManager() {
    }

    public void register(Class clazz) {
        if (inspectorMap.containsKey(clazz))
            throw new IllegalArgumentException("Class already registered");
        inspectorMap.put(clazz, new ClassInspector(clazz));
    }

    public boolean isRegistered(Class clazz) {
        return inspectorMap.containsKey(clazz);
    }

    public void inspectClass(Class clazz) {
        if (!isRegistered(clazz))
            register(clazz);
        ClassInspector inspector = inspectorMap.get(clazz);
        if (!inspector.isInspected())
            inspector.inspect();
    }

    public ClassInspector unregister(Class clazz) {
        return inspectorMap.remove(clazz);
    }

    public boolean isInspected(Class clazz) {
        if (inspectorMap.containsKey(clazz))
            return inspectorMap.get(clazz).isInspected();
        return false;
    }
}
