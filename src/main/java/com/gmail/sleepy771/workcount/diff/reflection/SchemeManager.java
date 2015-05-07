package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.patchers.Patcher;

import java.util.Map;

/**
 * Created by filip on 7.5.2015.
 */
public class SchemeManager implements Manager<PatcherScheme> {

    private Map<Class, PatcherScheme> schemes;

    private Map<Signature, Patcher> createPatcherMap(Class clazz) {
        ClassInspector inspector = ClassInspectorManager.getInstance().inspect(clazz);
        return null;
    }

    @Override
    public void register(PatcherScheme element) {
        if (isRegistered(element))
            throw new IllegalArgumentException("Already registered " + element.getPatchableClass().getName());
        schemes.put(element.getPatchableClass(), element);
    }

    @Override
    public void unregister(PatcherScheme element) {
        schemes.remove(element.getPatchableClass(), element);
    }

    @Override
    public boolean isRegistered(PatcherScheme element) {
        return schemes.containsKey(element.getPatchableClass());
    }
}
