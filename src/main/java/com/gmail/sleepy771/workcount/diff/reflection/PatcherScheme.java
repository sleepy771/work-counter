package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.annotations.AutoDifferentiable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by filip on 2.5.2015.
 */
public class PatcherScheme {

    List<Method> methods;

    Class objectClass;

    public Class getObjectClass() {
        return objectClass;
    }

    public void addMethod(Method m) {
        methods.add(m);
    }

    public boolean isValid(Patch patch) {
        return false;
    }

    public boolean isValid(Object object) {
        if (object.getClass().getAnnotation(AutoDifferentiable.class) == null) {
            throw new IllegalArgumentException("Object " + object.toString() + " is not AutoInspectable");
        }
        return false;
    }
}
