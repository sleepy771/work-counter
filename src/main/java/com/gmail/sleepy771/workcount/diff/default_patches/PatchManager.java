package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 8.5.2015.
 */
public class PatchManager extends AbstractManager<Class, Class<? extends Patch>> implements Manager<Class, Class<? extends Patch>>{


    @Override
    protected Class getKeyFromElement(Class<? extends Patch> element) throws ManagerException {
        ForClass forClass = element.getAnnotation(ForClass.class);
        if (forClass == null) {
            throw new ManagerException();
        }
        return forClass.forClass();
    }

    @Override
    protected void populate(Map<Class, Class<? extends Patch>> map) {
        map.put(String.class, StringPatch.class);
        map.put(Byte.class, NumberPatch.class);
        map.put(Short.class, NumberPatch.class);
        map.put(Integer.class, NumberPatch.class);
        map.put(Long.class, NumberPatch.class);
        map.put(Double.class, NumberPatch.class);
        map.put(Float.class, NumberPatch.class);
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return false;
    }

    @Override
    public Class<? extends Patch> get(Class key) throws ManagerException {
        return null;
    }

    @Override
    public Class<? extends Patch> remove(Class key) throws ManagerException {
        return null;
    }
}
