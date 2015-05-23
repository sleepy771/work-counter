package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.patchers.StringDeltaPatcher;

import java.util.Map;

/**
 * Created by filip on 5/22/15.
 */
public class DeltaManager extends AbstractManager<Class, Class<? extends Delta>> implements Manager<Class, Class<? extends Delta>> {
    @Override
    protected Class getKeyFromElement(Class<? extends Delta> element) throws ManagerException {
        ForClass forClass = element.getAnnotation(ForClass.class);
        if (forClass == null)
            throw new ManagerException("ForClass annotation not specified");
        return forClass.forClass();
    }

    @Override
    protected void populate(Map<Class, Class<? extends Delta>> map) {
        map.put(Byte.class, NumberDelta.class);
        map.put(Short.class, NumberDelta.class);
        map.put(Integer.class, NumberDelta.class);
        map.put(Long.class, NumberDelta.class);
        map.put(Float.class, NumberDelta.class);
        map.put(Double.class, NumberDelta.class);
        map.put(String.class, StringDelta.class);
    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public Class<? extends Delta> get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public Class<? extends Delta> remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }
}
