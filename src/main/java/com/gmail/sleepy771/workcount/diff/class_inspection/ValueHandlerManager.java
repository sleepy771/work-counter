package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.Releasable;
import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

/**
 * Created by filip on 6/1/15.
 */
public class ValueHandlerManager extends DefaultAbstractManager<Class<? extends ValueHandler>, ValueHandler> implements Releasable {

    private static ReleasableReference<ValueHandlerManager> INSTANCE = new ReleasableReference<>();

    private ReleasableManager releasableManager;

    private ValueHandlerManager() {
        super();
        if (ValueHandlerManager.INSTANCE != null) {
            INSTANCE.free();
        }
        ValueHandlerManager.INSTANCE.set(this);
    }

    @Override
    protected Class<? extends ValueHandler> getKeyForElement(ValueHandler element) {
        return element.getClass();
    }

    @Override
    public void free() {
        clear();
        INSTANCE.release(this);
        if (releasableManager != null)
            releasableManager.unregister(this);
    }

    public ValueHandler getHandler(Class<? extends ValueHandler> valueHandlerClass) throws IllegalAccessException, InstantiationException {
        if (!isRegisteredForKey(valueHandlerClass)) {
            ValueHandler handler = valueHandlerClass.newInstance();
            registerSilently(handler);
        }
        return getDirectly(valueHandlerClass);
    }

    @Override
    public void setReleasableManager(ReleasableManager r) {
        this.releasableManager = r;
    }

    public static ValueHandlerManager getInstance() {
        if (INSTANCE.isNull()) {
            return new ValueHandlerManager();
        }
        return INSTANCE.get();
    }

    public static ValueHandlerManager newInstance() {
        if (!INSTANCE.isNull()) {
            INSTANCE.free();
        }
        return new ValueHandlerManager();
    }
}
