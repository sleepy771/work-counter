package com.gmail.sleepy771.workcount.diff.object_construction;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 5/25/15.
 */
public class DefaultValuesManager extends DefaultAbstractManager<Class, DefaultValuesHolder> implements Manager<Class, DefaultValuesHolder>, Releasable{

    private static DefaultValuesManager INSTANCE;

    private ReleasableManager manager = null;


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
        manager.unregister(this);
        manager = null;
    }

    @Override
    public final void setReleasableManager(ReleasableManager r) {
        if (manager != null && manager.isRunningManager())
            manager = r;
    }

    @Override
    protected Class getKeyForElement(DefaultValuesHolder element) {
        return element.getForClass();
    }
}
