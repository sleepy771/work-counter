package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.sample.SimpleDiffObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 5/25/15.
 */
public class DefaultValuesManager extends DefaultAbstractManager<Class, DefaultValuesHolder> implements Manager<Class, DefaultValuesHolder>, Releasable{

    private static DefaultValuesManager INSTANCE;

    private ReleasableManager manager = null;

    @Override
    protected final Class getKeyFromElement(DefaultValuesHolder element) throws ManagerException {
        return element.getForClass();
    }

    @Override
    protected final void populate(Map<Class, DefaultValuesHolder> map) {
        map.put(SimpleDiffObject.class, new DefaultValuesHolder() {
            @Override
            public Class[] getParameterTypes() {
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

    public static DefaultValuesManager getInstance() throws ManagerException {
        if (null == INSTANCE) {
            INSTANCE = new DefaultValuesManager();
            ReleasableManager.getInstance().register(INSTANCE);
        }
        return INSTANCE;
    }

    public final void free() {
        clear();
        INSTANCE = null;
        try {
            manager.unregister(this);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
        manager = null;
    }

    @Override
    public final void setRegistrationManager(ReleasableManager r) {
        if (manager != null && manager.isRunningManager())
            manager = r;
    }
}
