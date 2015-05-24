package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;
import com.gmail.sleepy771.workcount.diff.scheme.SchemeManager;

import java.util.*;

/**
 * Created by filip on 5/17/15.
 */
public class MapPatch extends PatchBase implements Patch, Classy, Iterable<Map.Entry<Signature, Delta>> {

    public static final class Builder {

        private Map<Signature, Delta> patches;
        private Identificator id;
        private int fromVersion, toVersion;
        private Class forClass;

        private final Scheme manager;


        public Builder(MapPatch patch, Scheme manager) {
            this(patch.getForClass(), patch.getID(), patch.getFromVersion(), patch.getToVersion(), manager);
            putPatches(patch.patches);
        }

        public Builder(Class forClass, Identificator id, Scheme manager) {
            this(forClass, id, 0, 1, manager);
        }

        public Builder(Class forClass, Identificator id, int fromVersion, int toVersion, Scheme manager) {
            patches = new HashMap<>();
            this.forClass = forClass;
            this.id = id;
            this.fromVersion = fromVersion;
            this.toVersion = toVersion;
            this.manager = manager;
        }

        public Builder putPatches(Map<Signature, Delta> patches) {
            this.patches.putAll(patches);
            return this;
        }

        public Builder putPatch(Signature signature, Delta patch) {
            patches.put(signature, patch);
            return this;
        }

        public Builder setPatchMap(Map<Signature, Delta> patches) {
            this.patches = patches;
            return this;
        }

        public Builder removePatch(Signature signature) {
            patches.remove(signature);
            return this;
        }

        public Builder removePatch(Signature signature, Delta patchObject) {
            patches.remove(signature, patchObject);
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

        public Object getPatch(Signature signature) {
            return this.patches.get(signature);
        }

        public boolean hasPatch(Signature signature, Object patch) {
            if (patch == null) {
                return patches.containsKey(signature) && patches.get(signature) == null;
            }
            return patch.equals(patches.get(signature));
        }

        public boolean hasSignature(Signature signature) {
            return patches.containsKey(signature);
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

        public Builder setId(Identificator id) {
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

    private final Map<Signature, Delta> patches;
    private final Class forClass;

    private MapPatch(Class forClass, Identificator id, int fromVersion, int toVersion, Map<Signature, Delta> patches) {
        super(id, fromVersion, toVersion);
        this.forClass = forClass;
        this.patches = Collections.unmodifiableMap(patches);
    }

    @Override
    public final Class getForClass() {
        return forClass;
    }

    public final boolean hasPatchFor(Signature signature) {
        return patches.containsKey(signature);
    }

    @Override
    public Delta getDeltaFor(Signature signature) throws IllegalArgumentException {
        return patches.get(signature);
    }

    public final int getSize() {
        return patches.size();
    }

    @Override
    protected boolean hasEqualDeltas(Patch p) {
        if (p.hashCode() != hashCode())
            return false;
        Set<Signature> signatures = patches.keySet();
        for (Signature signature : signatures) {
            if (!(p.hasPatchFor(signature) && patches.get(signature).equals(p.getDeltaFor(signature))))
                return false;
        }
        return true;
    }

    @Override
    protected final int computeHash(int hash) {
        for (Map.Entry<Signature, Delta> signatureObjectEntry : this) {
            hash = 31 * hash + signatureObjectEntry.getKey().hashCode();
            hash = 31 * hash + signatureObjectEntry.getValue().hashCode();
        }
        return hash;
    }

    @Override
    public final Iterator<Map.Entry<Signature, Delta>> iterator() {
        return new Iterator<Map.Entry<Signature, Delta>>() {
            private final Iterator<Map.Entry<Signature, Delta>> entrySetIterator = patches.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return entrySetIterator.hasNext();
            }

            @Override
            public Map.Entry<Signature, Delta> next() {
                return entrySetIterator.next();
            }
        };
    }
}
