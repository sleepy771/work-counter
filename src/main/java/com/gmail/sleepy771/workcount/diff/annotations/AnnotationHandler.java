package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 5/24/15.
 */
public interface AnnotationHandler<A extends Annotation> {
    void onAnnotation(A annotation, Method method, Map<MethodSignature, Annotation> annotationMap);

    Class<? extends Annotation> getForAnnotation();
}
