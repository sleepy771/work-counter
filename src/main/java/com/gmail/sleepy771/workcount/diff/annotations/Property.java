package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.diff.DefaultPatchable;
import com.gmail.sleepy771.workcount.diff.DoNuttinHandler;
import com.gmail.sleepy771.workcount.diff.PreProcessHandler;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by filip on 30.4.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Property {

    String propertyName() default "-";

    PropertyType type() default PropertyType.GETTER;

    Class<? extends PreProcessHandler> preProcessingClass() default DoNuttinHandler.class;

    Class<? extends Patchable> patchAs() default DefaultPatchable.class;

    boolean needsInspection() default false;
}
