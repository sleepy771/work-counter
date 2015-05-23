package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by filip on 5/23/15.
 */
public class InstancePatch extends PatchBase implements Patch {

    private final Delta delta;
    private final Signature signature;

    public InstancePatch(Identificator id, int fromVersion, int toVersion, Class forClass, Delta delta) {
        super(id, fromVersion, toVersion);
        signature = new Signature(forClass);
        this.delta = delta;
    }

    @Override
    public Delta getDeltaFor(Signature signature) throws IllegalArgumentException {
        if (this.signature.equals(signature))
            return delta;
        throw new NoSuchElementException("Element not found");
    }

    @Override
    protected boolean hasEqualDeltas(Patch p) {
        Iterator<Map.Entry<Signature, Delta>> it1 = iterator();
        Iterator<Map.Entry<Signature, Delta>> it2 = p.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Map.Entry<Signature, Delta> o1 = it1.next();
            Map.Entry<Signature, Delta> o2 = it2.next();
            if (o1 == null ? o2 != null : !(o1.getKey().equals(o2.getKey()) && o1.getValue().equals(o2.getValue())))
                return false;
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    @Override
    protected int computeHash(int initSeed) {
        initSeed += signature.hashCode();
        initSeed = initSeed * 31 + delta.hashCode();
        return initSeed;
    }

    @Override
    public Iterator<Map.Entry<Signature, Delta>> iterator() {
        return new Iterator<Map.Entry<Signature, Delta>>() {

            private Map.Entry<Signature, Delta> entry = new Map.Entry<Signature, Delta>() {
                private final Delta delta = InstancePatch.this.delta;
                private final Signature signature = InstancePatch.this.signature;
                @Override
                public Signature getKey() {
                    return signature;
                }

                @Override
                public Delta getValue() {
                    return delta;
                }

                @Override
                public Delta setValue(Delta value) {
                    throw new UnsupportedOperationException("Set operation is not supported");
                }
            };

            @Override
            public boolean hasNext() {
                return entry != null;
            }

            @Override
            public Map.Entry<Signature, Delta> next() {
                if (entry == null)
                    throw new NoSuchElementException();
                try {
                    return entry;
                } finally {
                    entry = null;
                }
            }
        };
    }
}
