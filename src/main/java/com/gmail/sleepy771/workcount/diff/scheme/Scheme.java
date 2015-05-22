package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 9.5.2015.
 */
public interface Scheme extends Classy {

    Set<Signature> getPatchSignatures();

    Set<Signature> getUnsettableProperties();

    Set<Signature> getSettableProperties();

    Map<Signature, Signature> getSetterSignatures();

    List<Class<? extends Patch>> listPatchClasses();

    List<Class<? extends Patcher>> listPatcherClasses();

    List<Class<? extends Patchable>> listPatchableClasses();

    boolean hasMethod(Signature siganture);

    boolean hasSetter(Signature signature);

    boolean isValid(Patchable patchable);

    boolean isValid(Patch patch);

    boolean isValid(Patcher patcher);

    boolean onlyProperties();

    boolean onlySettableProperties();

    boolean hasSettableProperties();

    boolean hasUnsettableProperties();

    Method getMethod(Signature signature) throws NoSuchMethodException;

    Class<? extends Patcher> getPatcherClassForMethod(Signature signature);

    Class<? extends Patch> getPatchClassForMethod(Signature signature);

    Class<? extends Patchable> getPatchableClassForMethod(Signature signature);

    void invalidate();
}
