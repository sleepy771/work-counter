package com.gmail.sleepy771.workcount.diff.patchable_binders;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.PatchableWrapper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by filip on 1.5.2015.
 */
public class BoundsManager {

    private Map<Class<?>, Class<? extends Patchable>> boundsMap;

    public void bind(Class<?> clazz, Class<? extends Patchable> patchableClazz) {
        if (boundsMap.containsKey(clazz))
            throw new IllegalArgumentException("Class " + clazz.getName() + " already registered");
        boundsMap.put(clazz, patchableClazz);
    }

    public boolean isRegistered(Class<?> clazz) {
        return boundsMap.containsKey(clazz);
    }

    public Class<? extends Patchable> getPatchable(Class<?> clazz) {
        if (clazz.isAssignableFrom(Patchable.class))
            return (Class<? extends Patchable>) clazz;
        if (!boundsMap.containsKey(clazz))
            throw new IllegalArgumentException("Class is not bound to patchable");
        return boundsMap.get(clazz);
    }

    public <T> Patchable<T> createPatchable(final Class<T> clazz, final T object, final int version, final long id) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PatchableWrapper.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (method.getName().equals("getObject") && method.getReturnType() == Object.class) {
                    return clazz.cast(object);
                } else if (method.getName().equals("getVersion") && method.getReturnType() == Integer.class) {
                    return version;
                } else if (method.getName().equals("getId") && method.getReturnType() == Long.class) {
                    return id;
                }
                throw new RuntimeException("Unknown method");
            }
        });
        return (Patchable<T>) enhancer.create();
    }
}
