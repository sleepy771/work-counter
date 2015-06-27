package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.ValueHandler;
import com.gmail.sleepy771.workcount.diff.annotations.PatchProperty;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by filip on 6/19/15.
 */
public class MethodPropertyGetter extends AbstractPropertyGetter implements PropertyGetter {

    private Method getter;

    MethodPropertyGetter(Property property, Method getter) {
//        TODO check if method is valid getter
        super(new MethodSignature(getter), property);
        this.getter = getter;
    }

    @Override
    protected Object getDirectly(Object source) throws Exception {
        return getter.invoke(source);
    }
}
