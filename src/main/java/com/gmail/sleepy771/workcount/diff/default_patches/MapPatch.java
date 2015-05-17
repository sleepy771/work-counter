package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.diff.Identificator;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;

import java.util.*;

/**
 * Created by filip on 5/17/15.
 */
public class MapPatch extends PatchBase implements Patch {

    public static final class Builder implements Iterable<Map.Entry<Signature, Object>> {

        private final Map<Signature, Object> patches;
        private final Identificator id;
        private int fromVersion, toVersion;


        public Builder(MapPatch patch) {
            this(patch.getID(), patch.getFromVersion(), patch.getToVersion());
            putPatches(patch.patches);
        }

        public Builder(Identificator id) {
            this(id, 0, 1);
        }

        public Builder(Identificator id, int fromVersion, int toVersion) {
            patches = new HashMap<>();
            this.id = id;
            this.fromVersion = fromVersion;
            this.toVersion = toVersion;
        }

        public void putPatches(Map<Signature, Object> patches) {
            this.patches.putAll(patches);
        }

        public Object putPatch(Signature signature, Object patch) {
            return this.patches.put(signature, patch);
        }

        public Object removePatch(Signature signature) {
            return this.patches.remove(signature);
        }

        public void removePatch(Signature signature, Object patchObject) {
            this.patches.remove(signature, patchObject);
        }

        public void clearPatches() {
            this.patches.clear();
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

        public void validateVersions(int fromVersion, int toVersion) {
            if (fromVersion == toVersion)
                throw new IllegalArgumentException("Equal versions");
        }

        @Override
        //TODO move to MapPatch
        public Iterator<Map.Entry<Signature, Object>> iterator() {
            return new Iterator<Map.Entry<Signature, Object>>() {
                private final Iterator<Map.Entry<Signature, Object>> entrySetIterator = patches.entrySet().iterator();
                @Override
                public boolean hasNext() {
                    return entrySetIterator.hasNext();
                }

                @Override
                public Map.Entry<Signature, Object> next() {
                    return entrySetIterator.next();
                }
            };
        }
    }

    private final Map<Signature, Object> patches;

    private MapPatch(Identificator id, int fromVersion, int toVersion, Map<Signature, Object> patches) {
        super(id, fromVersion, toVersion);
        this.patches = patches;
    }
}
