package com.gmail.sleepy771.workcount.diff.scheme;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.class_inspection.PatchableInspector;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 9.5.2015.
 */
public class SchemeManager extends AbstractManager<Class, Scheme> implements Manager<Class, Scheme> {

    private final SchemeGenerator generator;

    public SchemeManager(SchemeGenerator generator) {
        this.generator = generator;
    }

    public SchemeGenerator getGenerator() {
        return this.generator;
    }

    public Scheme createSchemeAndRegister(PatchableInspector inspector) throws ManagerException{
        if (!isRegisteredForKey(inspector.getForClass())) {
            Scheme scheme = generator.createScheme(inspector);
            register(scheme);
            return scheme;
        }
        throw new ManagerException();
    }

    @Override
    protected Class getKeyFromElement(Scheme element) throws ManagerException {
        return element.getForClass();
    }

    @Override
    protected void populate(Map<Class, Scheme> map) {
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public Scheme get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public Scheme remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }
}
