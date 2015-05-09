package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.diff.DefaultPatchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by filip on 29.4.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface CanPatch {
    Class<? extends Patchable> asPatchable() default DefaultPatchable.class;
}
