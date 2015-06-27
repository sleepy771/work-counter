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
    public boolean isRegisteredForKey(Class key) {
        return false;
    }

    @Override
    public PatchableInspector get(Class key) throws ManagerException {
        return null;
    }

    @Override
    public PatchableInspector remove(Class key) {
        return removeDirectly(key);
    }

    @Override
    protected void populate() {

    }

    @Override
    protected void postRegistration(Class key, PatchableInspector element) {

    }

    @Override
    protected void postRelease(Class key, PatchableInspector element) {

    }

    @Override
    protected Class getKeyForElement(PatchableInspector element) {
        return element.getForClass();
    }
}
