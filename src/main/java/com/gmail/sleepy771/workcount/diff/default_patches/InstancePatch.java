package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Id;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by filip on 5/23/15.
 */
public class InstancePatch extends PatchBase implements Patch {

    private final Delta delta;
    private final MethodSignature methodSignature;

    public InstancePatch(Id id, int fromVersion, int toVersion, Class forClass, Delta delta) {
        super(id, fromVersion, toVersion);
        methodSignature = new MethodSignature(forClass);
        this.delta = delta;
    }

    @Override
    public Delta getDeltaFor(MethodSignature methodSignature) throws IllegalArgumentException {
        if (this.methodSignature.equals(methodSignature))
            return delta;
        throw new NoSuchElementException("Element not found");
    }

    @Override
    public boolean hasDeltaFor(MethodSignature methodSignature) {
        return this.methodSignature.equals(methodSignature);
    }

    @Override
    public boolean isPatch(MethodSignature methodSignature) {
        return false;
    }

    @Override
    protected boolean hasEqualDeltas(Patch p) {
        Iterator<Map.Entry<MethodSignature, Delta>> it1 = iterator();
        Iterator<Map.Entry<MethodSignature, Delta>> it2 = p.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            Map.Entry<MethodSignature, Delta> o1 = it1.next();
            Map.Entry<MethodSignature, Delta> o2 = it2.next();
            if (o1 == null ? o2 != null : !(o1.getKey().equals(o2.getKey()) && o1.getValue().equals(o2.getValue())))
                return false;
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    @Override
    protected int computeHash(int initSeed) {
        initSeed += methodSignature.hashCode();
        initSeed = initSeed * 31 + delta.hashCode();
        return initSeed;
    }

    @Override
    public Iterator<Map.Entry<MethodSignature, Delta>> iterator() {
        return new Iterator<Map.Entry<MethodSignature, Delta>>() {

            private Map.Entry<MethodSignature, Delta> entry = new Map.Entry<MethodSignature, Delta>() {
                private final Delta delta = InstancePatch.this.delta;
                private final MethodSignature methodSignature = InstancePatch.this.methodSignature;
                @Override
                public MethodSignature getKey() {
                    return methodSignature;
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
            public Map.Entry<MethodSignature, Delta> next() {
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

    @Override
    public Class getForClass() {
        return methodSignature.getType();
    }

    @Override
    public Object getPatch() {
        return delta.getPatch();
    }
}
