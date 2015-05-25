package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by filip on 5/24/15.
 */
public class MethodAnnotationManager extends AbstractManager<Class<? extends Annotation>, AnnotationHandler> implements Manager<Class<? extends Annotation>, AnnotationHandler> {
    @Override
    protected Class<? extends Annotation> getKeyFromElement(AnnotationHandler element) throws ManagerException {
        return element.getForAnnotation();
    }

    @Override
    protected void populate(Map<Class<? extends Annotation>, AnnotationHandler> map) {
    }

    @Override
    public boolean isRegisteredForKey(Class<? extends Annotation> key) {
        return containsKey(key);
    }

    @Override
    public AnnotationHandler get(Class<? extends Annotation> key) throws ManagerException {
        return getDirectly(key);
    }

    @Override
    public AnnotationHandler remove(Class<? extends Annotation> key) throws ManagerException {
        return removeDirectly(key);
    }
}
