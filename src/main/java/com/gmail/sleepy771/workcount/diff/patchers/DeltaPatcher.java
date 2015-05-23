package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

/**
 * Created by filip on 5/22/15.
 */
public interface DeltaPatcher {

    Object patch(Object original, Delta delta) throws PatcherException;

    Delta createDelta(Object original, Object altered) throws PatcherException;

    Delta invert(Delta delta) throws PatcherException;

    Object reverse(Object original, Delta delta) throws PatcherException;
}
