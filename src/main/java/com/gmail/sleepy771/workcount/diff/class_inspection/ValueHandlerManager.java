package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.SelfDereferenceManager;
import com.gmail.sleepy771.workcount.diff.ValueHandler;

/**
 * Created by filip on 6/5/15.
 */
public class ValueHandlerManager extends SelfDereferenceManager<Class<? extends ValueHandler>, ValueHandler> {

    private static ValueHandlerManager INSTANCE = null;

    public ValueHandlerManager() {
        if (INSTANCE != null) {
            clear();
            INSTANCE = null;
        }
        ValueHandlerManager.INSTANCE = this;
    }

    @Override
    protected void postRegister(Class<? extends ValueHandler> key, ValueHandler element) {
    }

    @Override
    protected void postUnregister(Class<? extends ValueHandler> key, ValueHandler element) {
    }

    @Override
    protected void populate() {
    }

    @Override
    protected Class<? extends ValueHandler> getKeyForElement(ValueHandler element) {
        return element.getClass();
    }

    public ValueHandler getHandler(Class<? extends ValueHandler> handlerClass) throws IllegalAccessException, InstantiationException {
        if (!isRegisteredForKey(handlerClass)) {
            ValueHandler handler = handlerClass.newInstance();
            putSilently(handlerClass, handler);
        }
        return getDirectly(handlerClass);
    }

    public static ValueHandlerManager getInstance() {
        if (INSTANCE == null) {
            return new ValueHandlerManager();
        }
        return INSTANCE;
    }
}
