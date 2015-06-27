package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Id;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

import java.util.*;

/**
 * Created by filip on 5/17/15.
 */
public class MapPatch extends PatchBase implements Patch, Classy, Iterable<Map.Entry<MethodSignature, Delta>> {

    @Override
    public Object getPatch() {
        return null;
    }

    public static final class Builder {

        private Map<MethodSignature, Delta> patches;
        private Id id;
        private int fromVersion, toVersion;
        private Class forClass;

        private final Scheme manager;


        public Builder(MapPatch patch, Scheme manager) {
            this(patch.getForClass(), patch.getID(), patch.getFromVersion(), patch.getToVersion(), manager);
            putPatches(patch.patches);
        }

        public Builder(Class forClass, Id id, Scheme manager) {
            this(forClass, id, 0, 1, manager);
        }

        public Builder(Class forClass, Id id, int fromVersion, int toVersion, Scheme manager) {
            patches = new HashMap<>();
            this.forClass = forClass;
            this.id = id;
            this.fromVersion = fromVersion;
            this.toVersion = toVersion;
            this.manager = manager;
        }

        public Builder putPatches(Map<MethodSignature, Delta> patches) {
            this.patches.putAll(patches);
            return this;
        }

        public Builder putPatch(MethodSignature methodSignature, Delta patch) {
            patches.put(methodSignature, patch);
            return this;
        }

        public Builder setPatchMap(Map<MethodSignature, Delta> patches) {
            this.patches = patches;
            return this;
        }

        public Builder removePatch(MethodSignature methodSignature) {
            patches.remove(methodSignature);
            return this;
        }

        public Builder removePatch(MethodSignature methodSignature, Delta patchObject) {
            patches.remove(methodSignature, patchObject);
            return this;
        }

        public Builder clearPatches() {
            this.patches.clear();
            return this;
        }

        public Builder setPatchedClass(Class clazz) {
            this.forClass = clazz;
            return this;
        }

        public Object getPatch(MethodSignature methodSignature) {
            return this.patches.get(methodSignature);
        }

        public boolean hasPatch(MethodSignature methodSignature, Object patch) {
            if (patch == null) {
                return patches.containsKey(methodSignature) && patches.get(methodSignature) == null;
            }
            return patch.equals(patches.get(methodSignature));
        }

        public boolean hasSignature(MethodSignature methodSignature) {
            return patches.containsKey(methodSignature);
        }

        private void validateVersions() {
            if (fromVersion == toVersion)
                throw new IllegalArgumentException("Equal versions");
        }

        private void validateID() {
            if (id == null)
                throw new IllegalArgumentException("Id should not be null");
        }

        public Builder setFromVersion(int version) {
            fromVersion = version;
            return this;
        }

        public Builder setId(Id id) {
            this.id = id;
            return this;
        }

        public Builder setToVersion(int version) {
            toVersion = version;
            return this;
        }

        public MapPatch build() {
            try {
                validateVersions();
                validateID();
                validateClass();
                validateThroughScheme();
                return new MapPatch(forClass, id, fromVersion, toVersion, patches);
            } finally {
                free();
            }
        }

        public void free() {
            toVersion = 1;
            fromVersion = 0;
            id = null;
            patches.clear();
        }

        public void validateClass() {
            if (forClass == null) {
                throw new IllegalArgumentException("Field forClass is null");
            }
        }
    }

    private final Map<MethodSignature, Delta> patches;
    private final Class forClass;

    private MapPatch(Class forClass, Id id, int fromVersion, int toVersion, Map<MethodSignature, Delta> patches) {
        super(id, fromVersion, toVersion);
        this.forClass = forClass;
        this.patches = Collections.unmodifiableMap(patches);
    }

    @Override
    public final Class getForClass() {
        return forClass;
    }

    public final boolean hasDeltaFor(MethodSignature methodSignature) {
        return patches.containsKey(methodSignature);
    }

    @Override
    public boolean isPatch(MethodSignature methodSignature) {
        return false;
    }

    @Override
    public Delta getDeltaFor(MethodSignature methodSignature) throws IllegalArgumentException {
        return patches.get(methodSignature);
    }

    public final int getSize() {
        return patches.size();
    }

    @Override
    protected boolean hasEqualDeltas(Patch p) {
        if (p.hashCode() != hashCode())
            return false;
        Set<MethodSignature> methodSignatures = patches.keySet();
        for (MethodSignature methodSignature : methodSignatures) {
            if (!(p.hasDeltaFor(methodSignature) && patches.get(methodSignature).equals(p.getDeltaFor(methodSignature))))
                return false;
        }
        return true;
    }

    @Override
    protected final int computeHash(int hash) {
        for (Map.Entry<MethodSignature, Delta> signatureObjectEntry : this) {
            hash = 31 * hash + signatureObjectEntry.getKey().hashCode();
            hash = 31 * hash + signatureObjectEntry.getValue().hashCode();
        }
        return hash;
    }

    @Override
    public final Iterator<Map.Entry<MethodSignature, Delta>> iterator() {
        return new Iterator<Map.Entry<MethodSignature, Delta>>() {
            private final Iterator<Map.Entry<MethodSignature, Delta>> entrySetIterator = patches.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return entrySetIterator.hasNext();
            }

            @Override
            public Map.Entry<MethodSignature, Delta> next() {
                return entrySetIterator.next();
            }
        };
    }
}
