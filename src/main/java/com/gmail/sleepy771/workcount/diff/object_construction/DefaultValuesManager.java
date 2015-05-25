package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.sample.SimpleDiffObject;

import java.util.Map;

/**
 * Created by filip on 5/25/15.
 */
public class DefaultValuesManager extends AbstractManager<Class, DefaultValuesHolder> implements Manager<Class, DefaultValuesHolder>{
    @Override
    protected Class getKeyFromElement(DefaultValuesHolder element) throws ManagerException {
        return element.getForClass();
    }

    @Override
    protected void populate(Map<Class, DefaultValuesHolder> map) {
        map.put(SimpleDiffObject.class, new DefaultValuesHolder() {
            @Override
            public Class[] parameterTypes() {
                return new Class[]{String.class, String.class};
            }

            @Override
            public Object[] getDefaultValues() {
                return new Object[]{"", ""};
            }

            @Override
            public Class getForClass() {
                return SimpleDiffObject.class;
            }
        });

    }

    @Override
    public boolean isRegisteredForKey(Class key) {
        return containsKey(key);
    }

    @Override
    public DefaultValuesHolder get(Class key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public DefaultValuesHolder remove(Class key) throws ManagerException {
        return removeDirectly(key);
    }
}
