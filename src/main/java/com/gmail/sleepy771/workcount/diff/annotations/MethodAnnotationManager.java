package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.AbstractManager;
import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by filip on 5/24/15.
 */
public class MethodAnnotationManager extends DefaultAbstractManager<Class<? extends Annotation>, AnnotationHandler> implements Manager<Class<? extends Annotation>, AnnotationHandler> {
    @Override
    @SuppressWarnings("unchecked")
    protected Class<? extends Annotation> getKeyForElement(AnnotationHandler element) {
        return element.getForAnnotation();
    }
}
