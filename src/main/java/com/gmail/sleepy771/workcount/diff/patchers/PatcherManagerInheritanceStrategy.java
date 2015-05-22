package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;

import java.util.*;

/**
 * Created by filip on 5/18/15.
 */
public class PatcherManagerInheritanceStrategy extends AbstractManager<Class, Patcher> implements Manager<Class, Patcher>, Patcher {

    @Override
    public boolean isRegisteredForKey(Class key) {
        return findRegisteredInHierarchy(key) != null;
    }

    @Override
    public Patcher get(Class key) throws ManagerException {
        Class registered = findRegisteredInHierarchy(key);
        if (registered == null)
            throw new ManagerException();
        return getDirectly(registered);
    }

    @Override
    public Patcher remove(Class key) throws ManagerException {
        Map<Class, Patcher> removed = new HashMap<>();
        for (Map.Entry<Class, Patcher> entry : this) {
            if (entry.getKey().isInstance(key)) {
                removed.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Class, Patcher> entry : removed.entrySet()) {
            removeDirectly(entry.getKey());
        }
        return new CompositePatcher(key, removed);
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        return null;
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    protected Class getKeyFromElement(Patcher element) throws ManagerException {
        if (Classy.class.isInstance(element.getClass())) {
            Classy classyElement = ((Classy) element);
            return classyElement.getForClass();
        }
        ForClass forClass = element.getClass().getAnnotation(ForClass.class);
        return forClass.forClass();
    }

    public Class getRegisteredAs(Class key) {
        return findRegisteredInHierarchy(key);
    }

    @Override
    protected void populate(Map<Class, Patcher> map) {

    }

    protected final Class findRegisteredInHierarchy(Class clazz) {
        Set<Class> searched = new HashSet<>();
        LinkedList<Class> popinStack = new LinkedList<>();
        popinStack.addLast(clazz);
        Class interestedIn = null;
        while (!popinStack.isEmpty()) {
            interestedIn = popinStack.pollLast();
            if (containsKey(interestedIn))
                return interestedIn;
            searched.add(interestedIn);
            for (Class iFace : interestedIn.getInterfaces()) {
                if (!searched.contains(iFace))
                    popinStack.add(iFace);
            }
            Class superClass = interestedIn.getSuperclass();
            if (superClass != null & !searched.contains(superClass))
                popinStack.add(superClass);
        }
        return null;
    }
}
