package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.reflection.Signature;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 5/17/15.
 */
public class ValueInjector {

    public Object injectValues(Class objectClass, final Map<Signature, Object> valuesMap) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectClass);
        CallbackHelper callbackHelper = new CallbackHelper(objectClass, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                final Object value = valuesMap.get(new Signature(method));
                if (value != null) {
                    return (FixedValue) () -> value;
                }
                return NoOp.INSTANCE;
            }
        };
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        return enhancer.create();
    }
 }
