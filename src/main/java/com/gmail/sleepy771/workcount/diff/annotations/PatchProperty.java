package com.gmail.sleepy771.workcount.diff.annotations;

import com.gmail.sleepy771.workcount.diff.AsSelf;
import com.gmail.sleepy771.workcount.diff.DoNuttinHandler;
import com.gmail.sleepy771.workcount.diff.ValueHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by filip on 30.4.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface PatchProperty {

    String name() default "-";

    PropertyType type() default PropertyType.GETTER;

    Class<? extends ValueHandler> fromTypeHandlerClass() default DoNuttinHandler.class;

    Class<? extends ValueHandler> toTypeHandlerClass() default DoNuttinHandler.class;

    Class<? extends ValueHandler> valueHandlerClass() default DoNuttinHandler.class;

    Class patchAs() default AsSelf.class;

    Class setAs() default AsSelf.class;
}
