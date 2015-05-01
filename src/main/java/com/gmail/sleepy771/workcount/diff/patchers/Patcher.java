package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;

/**
 * Created by filip on 29.4.2015.
 */
public interface Patcher<R, T extends Patchable<R>, P extends Patch<T>> {
    T patch(T original, P patch);

    P createPatch(T original, T altered);

    P reverse(T original, P patch);

    Class<T> getPatchableClass();
}
