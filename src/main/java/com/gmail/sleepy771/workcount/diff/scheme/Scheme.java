package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by filip on 9.5.2015.
 */
public interface Scheme extends Classy {

    List<Signature> listMethods();

    List<Class<? extends Patch>> listPatchClasses();

    List<Class<? extends Patcher>> listPatcherClasses();

    List<Class<? extends Patchable>> listPatchableClasses();

    boolean hasMethod(Signature siganture);

    boolean isValid(Patchable patchable);

    boolean isValid(Patch patch);

    boolean isValid(Patcher patcher);

    Class<? extends Patcher> getPatcherClassForMethod(Signature signature);

    Class<? extends Patch> getPatchClassForMethod(Signature signature);

    Class<? extends Patchable> getPatchableClassForMethod(Signature signature);
}
