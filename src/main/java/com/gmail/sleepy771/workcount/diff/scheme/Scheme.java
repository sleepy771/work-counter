package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 9.5.2015.
 */
public interface Scheme extends Classy {

    Set<MethodSignature> getPatchSignatures();

    Set<MethodSignature> getUnsettableProperties();

    Set<MethodSignature> getSettableProperties();

    Map<MethodSignature, MethodSignature> getSetterSignatures();

    List<Class<? extends Patch>> listPatchClasses();

    List<Class<? extends Patcher>> listPatcherClasses();

    List<Class<? extends Patchable>> listPatchableClasses();

    boolean hasMethod(MethodSignature methodSignature);

    boolean hasSetter(MethodSignature methodSignature);

    boolean isValid(Patchable patchable);

    boolean isValid(Patch patch);

    boolean isValid(Patcher patcher);

    boolean onlyProperties();

    boolean onlySettableProperties();

    boolean hasSettableProperties();

    boolean hasUnsettableProperties();

    Method getMethod(MethodSignature methodSignature) throws NoSuchMethodException;

    Class<? extends Patcher> getPatcherClassForMethod(MethodSignature methodSignature);

    Class<? extends Patch> getPatchClassForMethod(MethodSignature methodSignature);

    Class<? extends Patchable> getPatchableClassForMethod(MethodSignature methodSignature);

    void invalidate();
}
