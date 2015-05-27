package com.gmail.sleepy771.workcount.diff.default_patches;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.annotations.ForClass;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.Map;

/**
 * Created by filip on 5/22/15.
 */
public class DeltaManager extends DefaultAbstractManager<Class, Class<? extends Delta>> implements Manager<Class, Class<? extends Delta>> {
    @Override
    protected Class getKeyForElement(Class<? extends Delta> element) {
        ForClass forClass = element.getAnnotation(ForClass.class);
        return null == forClass ? null : forClass.forClass();
    }

    @Override
    protected void populate() {
        registerSilently(Byte.class, NumberDelta.class);
        registerSilently(Short.class, NumberDelta.class);
        registerSilently(Integer.class, NumberDelta.class);
        registerSilently(Long.class, NumberDelta.class);
        registerSilently(Float.class, NumberDelta.class);
        registerSilently(Double.class, NumberDelta.class);
        registerSilently(String.class, StringDelta.class);
    }
}
