package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.annotations.AutoDifferentiable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 2.5.2015.
 */
public class PatcherScheme {

    private final Map<Signature, Patcher> patchers;
    private final Class patchableClass;

    public PatcherScheme(Class patchableClass, Map<Signature, Patcher> patchers) {
        this.patchableClass = patchableClass;
        this.patchers = Collections.unmodifiableMap(patchers);
    }

    public Map<Signature, Patcher> getPatchers() {
        return patchers;
    }

    public Class getPatchableClass() {
        return patchableClass;
    }
}
