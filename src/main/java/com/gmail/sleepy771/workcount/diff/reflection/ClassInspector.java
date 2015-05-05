package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;
import com.gmail.sleepy771.workcount.diff.annotations.RecursiveInspect;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.patchers.AbstractPatcher;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by filip on 2.5.2015.
 */
public class ClassInspector {

    private final Class clazzUnderInspection;

    public ClassInspector(Class clazz) {
        this.clazzUnderInspection = clazz;
    }

    public Class getInspectedClass() {
        return clazzUnderInspection;
    }

    public boolean isInspected() {
        return false;
    }

    public void inspect() {

    }

    public Map<Signature, PatchableProperty> getPatchableProperties() {
        return null;
    }

    public Map<Signature, Class> getRecursivelyInspectable() {
        return null;
    }

    public boolean needsRecursiveInspection() {
        return false;
    }

    public boolean isPatchable() {
        return false;
    }

    public boolean validatePatcher(Class<? extends Patcher> p) {
        return false;
    }

    public boolean validatePatch(Class<? extends Patch> p) {
        return false;
    }

    public void bindPatch(Class<? extends Patch> p) {

    }
}
