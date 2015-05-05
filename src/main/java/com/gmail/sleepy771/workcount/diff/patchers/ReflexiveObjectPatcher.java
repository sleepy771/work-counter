package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.DefaultPatchable;
import com.gmail.sleepy771.workcount.diff.annotations.AutoDifferentiable;
import com.gmail.sleepy771.workcount.diff.annotations.PatchableProperty;
import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.ReflexiveObjectPatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.ReflexiveObjectPatch;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by filip on 1.5.2015.
 */
public class ReflexiveObjectPatcher extends AbstractPatcher<Object, ReflexiveObjectPatchable, ReflexiveObjectPatch> implements Patcher<Object, ReflexiveObjectPatchable, ReflexiveObjectPatch>{
    @Override
    public ReflexiveObjectPatchable patch(ReflexiveObjectPatchable original, ReflexiveObjectPatch patch) {
        checkIds(original, patch);
        return null;
    }

    @Override
    public ReflexiveObjectPatch createPatch(ReflexiveObjectPatchable original, ReflexiveObjectPatchable altered) {
        checkIds(original, altered);
        return null;
    }

    @Override
    public ReflexiveObjectPatch reverse(ReflexiveObjectPatchable original, ReflexiveObjectPatch patch) {
        checkIds(original, patch);
        return null;
    }

    @Override
    public Class<ReflexiveObjectPatchable> getPatchableClass() {
        return ReflexiveObjectPatchable.class;
    }

    private void makeInspection(Object o) {
        if (o.getClass().getAnnotation(AutoDifferentiable.class) == null) {
            throw new IllegalArgumentException("This class can not be inspected");
        }

        Method[] methods = o.getClass().getDeclaredMethods();
        Map<String, Class<?>> scheme = new HashMap<>();
        for (Method method : methods) {
            PatchableProperty patchableMethodAnnotation;
            if ((patchableMethodAnnotation = method.getAnnotation(PatchableProperty.class)) != null) {
                if (method.getParameterCount() != 0) {
                    throw new IllegalArgumentException("This method is not a property");
                }
                if (patchableMethodAnnotation.patchAs().equals(DefaultPatchable.class)) {
                    if (method.getReturnType().isAssignableFrom(Patchable.class)) {
                        scheme.put(method.getName(), method.getReturnType());
                    } else {

                    }
                } else {
                    scheme.put(method.getName(), patchableMethodAnnotation.patchAs());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Patchable<T> createProxyPatchable(Class<T> patchableClass, final long id, final int version, final T object) {
        final Map<Signature, FixedValue> methodInterceptorMap = new HashMap<>();
        methodInterceptorMap.put(new Signature("getId", new Class[0], long.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return id;
            }
        });
        methodInterceptorMap.put(new Signature("getVersion", new Class[0], int.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return version;
            }
        });
        methodInterceptorMap.put(new Signature("getObject", new Class[0], Object.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return object;
            }
        });
        Enhancer enhancer = new Enhancer();
        CallbackHelper helper = new CallbackHelper(patchableClass, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                Signature signature = new Signature(method);
                if (methodInterceptorMap.containsKey(signature))
                    return methodInterceptorMap.get(signature);
                return NoOp.INSTANCE;
            }
        };
        enhancer.setSuperclass(patchableClass);
        enhancer.setCallbackFilter(helper);
        enhancer.setCallbacks(helper.getCallbacks());
        return (Patchable<T>) enhancer.create();
    }
}
