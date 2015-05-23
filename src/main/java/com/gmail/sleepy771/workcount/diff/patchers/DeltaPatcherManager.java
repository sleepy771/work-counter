package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.patchers.DeltaPatcher;

import java.util.Map;

/**
 * Created by filip on 5/22/15.
 */
public class DeltaPatcherManager extends AbstractManager<Class, ClassyDeltaPatcher> implements Manager<Class, ClassyDeltaPatcher>, DeltaPatcher {
    @Override
    protected Class getKeyFromElement(ClassyDeltaPatcher element) throws ManagerException {
        return element.getForClass();
    }

    @Override
    protected void populate(Map<Class, ClassyDeltaPatcher> map) {
        NumberDeltaPatcher numberPatcher = new NumberDeltaPatcher();
        map.put(Byte.class, numberPatcher);
        map.put(Short.class, numberPatcher);
        map.put(Integer.class, numberPatcher);
        map.put(Long.class, numberPatcher);
        map.put(Float.class, numberPatcher);
        map.put(Double.class, numberPatcher);
        map.put(String.class, new StringDeltaPatcher());
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public ClassyDeltaPatcher get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public ClassyDeltaPatcher remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }

    @Override
    public Object patch(Object original, Delta delta) throws PatcherException {
        validateClasses(original.getClass(), delta.getForClass());
        try {
            return get(original.getClass()).patch(original, delta);
        } catch (ManagerException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Delta createDelta(Object original, Object altered) throws PatcherException {
        validateClasses(original.getClass(), altered.getClass());
        try {
            return get(original.getClass()).createDelta(original, altered);
        } catch (ManagerException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Delta invert(Delta delta) throws PatcherException {
        try {
            return get(delta.getForClass()).invert(delta);
        } catch (ManagerException e) {
            throw new PatcherException(e);
        }
    }

    @Override
    public Object reverse(Object original, Delta delta) throws PatcherException {
        validateClasses(original.getClass(), delta.getForClass());
        try {
            return get(original.getClass()).reverse(original, delta);
        } catch (ManagerException e) {
            throw new PatcherException(e);
        }
    }

    private void validateClasses(Class classA, Class classB) throws PatcherException {
        if (!classA.equals(classB))
            throw new PatcherException("Unequal types");
    }
}
