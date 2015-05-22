package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.ManagerWithDependencies;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableManager;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.default_patches.PatchManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

import java.util.List;
import java.util.Map;

/**
 * Created by filip on 8.5.2015.
 */
public class PatcherManager extends AbstractManager<Class, Patcher> implements Patcher, ManagerWithDependencies<Class, Patcher> {

    private final PatchManager patchManager;

    private final PatchableManager patchableManager;

    public PatcherManager(PatchManager patchManager, PatchableManager patchableManager) {
        this.patchManager = patchManager;
        this.patchableManager = patchableManager;

    }

    public PatchManager getPatchManager() {
        return patchManager;
    }

    public PatchableManager getPatchableManager() {
        return patchableManager;
    }

    @Override
    public List<Patcher> removeWithDependencies(Class key) {
        return null;
    }

    @Override
    protected Class getKeyFromElement(Patcher element) throws ManagerException {
        ForClass forClass = element.getClass().getAnnotation(ForClass.class);
        if (forClass != null) {
            return forClass.forClass();
        }
        if (Classy.class.isInstance(element)) {
            return ((Classy) element).getForClass();
        }
        throw new ManagerException();
    }

    @Override
    protected void populate(Map<Class, Patcher> map) {
    }

    public Patch createInitialPatch(Object from, Object to) {
        return null;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) {
        return null;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) {
        return null;
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) {
        return null;
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) {
        return null;
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public Patcher get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public Patcher remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }
}
