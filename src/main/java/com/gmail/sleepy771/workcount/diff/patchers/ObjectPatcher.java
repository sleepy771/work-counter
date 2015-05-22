package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by filip on 5/17/15.
 */
public class ObjectPatcher implements Patcher, Classy {

    private final Scheme scheme;

    private final PatcherManager manager;

    public ObjectPatcher(Scheme scheme, PatcherManager manager) {
        this.scheme = scheme;
        this.manager = manager;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        scheme.isValid(patch);
        scheme.isValid(original);
        Map<Signature, Patchable> patchedMap = new HashMap<>();
        for (Signature signature : scheme.getPatchSignatures()) {
            try {
                Patcher patcher = manager.get(signature.getReturnType());
                Method property = original.getForClass().getMethod(signature.getMethodName(), signature.getParametersType());
                Patchable patchableValue = makePatchable(original, property.invoke(original));
                patchedMap.put(signature, patcher.patch(patchableValue, patch.getDeltaFor(signature)));
            } catch (ManagerException e) {
                throw new PatcherException(e);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                scheme.invalidate();
                throw new PatcherException(e);
            }
        }
        Object patchedObject = null;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(original.getForClass());
        CallbackHelper helper = new CallbackHelper(original.getForClass(), new Class[0]) {
            private Map<Signature, Object> excluded = selectByKeys(patchedMap, scheme.getUnsettableProperties());
            @Override
            protected Object getCallback(Method method) {
                Signature methodSignature = new Signature(method);
                if (excluded.containsKey(methodSignature))
                    return (FixedValue) () -> excluded.get(methodSignature);
                return NoOp.INSTANCE;
            }
        };
        enhancer.setCallbackFilter(helper);
        enhancer.setCallbacks(helper.getCallbacks());
        patchedObject = enhancer.create();
        for (Map.Entry<Signature, Signature> setterToProperty : scheme.getSetterSignatures().entrySet()) {
            try {
                Method setter = scheme.getForClass().getMethod(setterToProperty.getKey().getMethodName(), setterToProperty.getKey().getParametersType());
                setter.invoke(patchedObject, patchedMap.get(setterToProperty.getValue()));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                scheme.invalidate();
                throw new PatcherException(e);
            }
        }
        return new SimplePatchable(original.getID(), patch.getToVersion(), patchedObject);
    }

    private Patchable makePatchable(Patchable originalObject, Object value) {
        if (Patchable.class.isInstance(value))
            return ((Patchable) value);
        return new SimplePatchable(originalObject.getID(), originalObject.getVersion(), value);
    }

    private Map<Signature, Object> selectByKeys(Map<Signature, Patchable> patchableMap, Set<Signature> signatureSet) {
        Map<Signature, Object> selected = new HashMap<>();
        for (Signature sgn : signatureSet) {
            selected.put(sgn, patchableMap.get(sgn).getValue());
        }
        return selected;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        scheme.isValid(original);
        scheme.isValid(altered);
        Map<Signature, Patch> patchMap = new HashMap<>();
        for (Signature signature : scheme.getPatchSignatures()) {
            try {
                Method property = scheme.getMethod(signature);
                Object originalValue = property.invoke(original.getValue());
                Object alteredValue = property.invoke(altered.getValue());
                Patcher patcher = manager.get(property.getReturnType());
                Patch valuePatch = patcher.createPatch(new SimplePatchable(original.getID(), original.getVersion(), originalValue), new SimplePatchable(altered.getID(), altered.getVersion(), alteredValue));
                patchMap.put(signature, valuePatch);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                scheme.invalidate();
                throw new PatcherException(e);
            } catch (ManagerException e) {
                throw new PatcherException(e);
            }
        }
        return null;
    }

    @Override
    public Patchable revert(Patchable original, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Patch invert(Patchable patchable, Patch patch) throws PatcherException {
        return null;
    }

    @Override
    public Class getForClass() {
        return scheme.getForClass();
    }
}
