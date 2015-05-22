package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 5/22/15.
 */
public class DeltaPatchManager extends AbstractManager<Class, DeltaPatcher> implements Manager<Class, DeltaPatcher> {
    @Override
    protected Class getKeyFromElement(DeltaPatcher element) throws ManagerException {
        return null;
    }

    @Override
    protected void populate(Map<Class, DeltaPatcher> map) {

    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return false;
    }

    @Override
    public DeltaPatcher get(Class key) throws ManagerException {
        return null;
    }

    @Override
    public DeltaPatcher remove(Class key) throws ManagerException {
        return null;
    }
}
