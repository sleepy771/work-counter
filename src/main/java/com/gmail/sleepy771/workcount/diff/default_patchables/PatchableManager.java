package com.gmail.sleepy771.workcount.diff.default_patchables;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 8.5.2015.
 */
public class PatchableManager extends AbstractManager<Class, Class<? extends Patchable>> implements Manager<Class, Class<? extends Patchable>> {
    @Override
    protected Class getKeyFromElement(Class<? extends Patchable> element) throws ManagerException {
        ForClass forClass = element.getAnnotation(ForClass.class);
        if (forClass == null)
            throw new ManagerException();
        return forClass.forClass();
    }

    @Override
    protected void populate(Map<Class, Class<? extends Patchable>> map) {
    }

    @Override
    public final boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public final Class<? extends Patchable> get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public final Class<? extends Patchable> remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }
}
