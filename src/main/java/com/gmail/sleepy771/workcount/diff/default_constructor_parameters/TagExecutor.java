package com.gmail.sleepy771.workcount.diff.default_constructor_parameters;

import com.gmail.sleepy771.workcount.ReleasableManager;
import com.gmail.sleepy771.workcount.diff.Releasable;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 5/28/15.
 */
public class TagExecutor implements Releasable {

    private static TagExecutor INSTANCE;

    private ReleasableManager releasableManager;

    private ConstructorParametersManager constructorParametersManager;

    private Map<String, Object> instanceMap;

    private TagManager tagManager;

    public void executeTag(InstanceTag tag) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object instance = null;
        if (tag.isStatic()) {
            Class clazz = tag.getDeclaredClass();
            if ("new".equals(tag.invoke())) {
                Constructor constructor = clazz.getConstructor(tag.getParameterTypes());
                instance = constructor.newInstance(tag.getParameters());
            } else {
                Method method = clazz.getMethod(tag.invoke());
                if (!Modifier.isStatic(method.getModifiers()))
                    throw new IllegalAccessException();
                instance = method.invoke(tag.invoke(), tag.getParameters());
            }
        } else {
            Method method = tag.getDeclaredInstance().getClass().getMethod(tag.invoke(), tag.getParameterTypes());
            instance = method.invoke(tag.getDeclaredClass(), tag.getParameters());
        }
        instanceMap.put(tag.getReferenceName(), instance);
    }

    private TagExecutor() {
        releasableManager = new ReleasableManager();
        constructorParametersManager = new ConstructorParametersManager();
        tagManager = new TagManager();
        instanceMap = new HashMap<>();

        register();
    }

    private void register() {
        try {
            releasableManager.register(this);
            releasableManager.register(constructorParametersManager);
            releasableManager.register(tagManager);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void free() {
        INSTANCE = null;
        constructorParametersManager = null;
        tagManager = null;
        releasableManager.free();
    }

    @Override
    public void setReleasableManager(ReleasableManager r) {
        releasableManager = r;
    }

    public static TagExecutor getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new TagExecutor();
        }
        return INSTANCE;
    }
}
