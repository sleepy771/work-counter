package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.HasID;
import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by filip on 5/23/15.
 */
public class InstancePatch extends PatchBase implements Patch {

    private final Delta delta;

    public InstancePatch(Identificator id, int fromVersion, int toVersion, Delta delta) {
        super(id, fromVersion, toVersion);
        this.delta = delta;
    }

    @Override
    public Delta getDeltaFor(Signature signature) throws IllegalArgumentException {
        return delta;
    }

    @Override
    protected boolean compareDeltas(Patch p) {
        return false;
    }

    @Override
    protected int computeHash(int initSeed) {
        return 0;
    }

    @Override
    public Iterator<Map.Entry<Signature, Delta>> iterator() {
        return null;
    }
}
