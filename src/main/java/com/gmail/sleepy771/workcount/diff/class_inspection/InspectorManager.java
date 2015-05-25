package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 8.5.2015.
 */
public class InspectorManager extends AbstractManager<Class, PatchableInspector> implements Manager<Class, PatchableInspector> {
    @Override
    protected Class getKeyFromElement(PatchableInspector element) {
        return element.getForClass();
    }

    @Override
    protected void populate(Map<Class, PatchableInspector> map) {
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return false;
    }

    @Override
    public PatchableInspector get(Class key) throws ManagerException {
        return null;
    }

    @Override
    public PatchableInspector remove(Class key) throws ManagerException {
        return null;
    }
}
