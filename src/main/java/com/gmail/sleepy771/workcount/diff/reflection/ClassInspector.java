package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 6.5.2015.
 */
public class ClassInspector {

    private final Class inspectedClass;

    private Map<Signature, PatchableProperty> propertyMap;
    private Map<Signature, PatchableProperty> recursiveInspectionMap;
    private boolean isValid;

    public ClassInspector(Class inspectedClass) {
        isValid = false;
        propertyMap = null;
        recursiveInspectionMap = null;
        this.inspectedClass = inspectedClass;
    }

    public Class getInspectedClass() {
        return inspectedClass;
    }

    public void runInspection() {
        getPropertyMap();
    }

    public Map<Signature, PatchableProperty> getPropertyMap() {
        if (propertyMap == null) {
            Map<Signature, PatchableProperty> properties = new HashMap<>();
            for (Method method : inspectedClass.getMethods()) {
                PatchableProperty property = method.getAnnotation(PatchableProperty.class);
                if (property != null) {
                    isValid = isProperty(method);
                    properties.put(new Signature(method), property);
                }
            }
            propertyMap = Collections.unmodifiableMap(properties);
        }
        return propertyMap;
    }

    public Map<Signature, PatchableProperty> getRecursivePropertyMap() {
        if (recursiveInspectionMap == null) {
            Map<Signature, PatchableProperty> properties = new HashMap<>();
            getPropertyMap().entrySet().stream().filter(entry -> entry.getValue().needsInspection()).forEach(entry -> properties.put(entry.getKey(), entry.getValue()));
            recursiveInspectionMap = Collections.unmodifiableMap(properties);
        }
        return recursiveInspectionMap;
    }

    public boolean needsRecursiveInspection() {
        return  getRecursivePropertyMap().size() != 0;
    }

    public boolean isPatchable() {
        return getPropertyMap().size() != 0;
    }

    public boolean isValid() {
        getPropertyMap();
        return isValid;
    }

    public void validate() {
        if (!isValid())
            throw new IllegalArgumentException("Invalid Class");
    }

    @Override
    public int hashCode() {
        return 17 * 31 + inspectedClass.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return ClassInspector.class.equals(o.getClass()) && inspectedClass.equals(((ClassInspector) o).inspectedClass);
    }

    private boolean isProperty(Method method) {
        return !void.class.equals(method.getReturnType()) && method.getParameterCount() == 0;
    }
}
