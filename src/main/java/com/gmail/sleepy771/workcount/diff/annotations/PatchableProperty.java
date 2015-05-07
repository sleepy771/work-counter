package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.diff.DefaultPatchable;
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

public @interface PatchableProperty {
    Class<? extends Patchable> patchAs() default DefaultPatchable.class;

    boolean needsInspection() default false;
}
