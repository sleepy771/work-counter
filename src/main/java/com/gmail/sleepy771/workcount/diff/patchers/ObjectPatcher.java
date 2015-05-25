package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.default_patches.MapPatch;
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
public class ObjectPatcher extends AbstractPatcher implements Patcher, Classy {

    private final Scheme scheme;

    private final DeltaPatcherManager deltaPatcherManager;

    private final PatcherManager patcherManager;

    public ObjectPatcher(Scheme scheme, DeltaPatcherManager patcher, PatcherManager manager) {
        this.scheme = scheme;
        this.deltaPatcherManager = patcher;
        patcherManager = manager;
    }

    @Override
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        scheme.isValid(patch);
        scheme.isValid(original);
        validateIds(original, patch);
        validateFromVersion(original, patch);
        Map<Signature, Object> patchedMap = new HashMap<>();
        Object originalObject = original.getValue();
        for (Signature signature : scheme.getPatchSignatures()) {
            try {
                Method property = original.getForClass().getMethod(signature.getMethodName(), signature.getParametersType());
                Object value = property.invoke(originalObject);
                if (patch.hasDeltaFor(signature)) {
                    if (patch.isPatch(signature)) {
                        if (!patcherManager.isRegisteredForKey(signature.getReturnType())) {
                            // atempt to run ClassInspector
                        }
                        Patcher patcher = patcherManager.get(signature.getReturnType());
                        Patchable originalInnerObjectPatchable = new SimplePatchable(original.getID(), original.getVersion(), value);
                        Patch innerObjectPatch = (Patch) patch.getDeltaFor(signature);
                        Patchable patchedInnerObjectPatchable = patcher.patch(originalInnerObjectPatchable, innerObjectPatch);
                        patchedMap.put(signature, patchedInnerObjectPatchable.getValue());
                    } else {
                        DeltaPatcher deltaPatcher = deltaPatcherManager.get(signature.getReturnType());
                        Delta delta = patch.getDeltaFor(signature);
                        Object patchedInnerObject = deltaPatcher.patch(value, delta);
                        patchedMap.put(signature, patchedInnerObject);
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                scheme.invalidate();
                throw new PatcherException(e);
            } catch (ManagerException e) {
                throw new PatcherException(e);
            }
        }
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
        Object patchedObject = enhancer.create();
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

    private Map<Signature, Object> selectByKeys(Map<Signature, Object> patchableMap, Set<Signature> signatureSet) {
        Map<Signature, Object> selected = new HashMap<>();
        for (Signature sgn : signatureSet) {
            selected.put(sgn, patchableMap.get(sgn));
        }
        return selected;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        scheme.isValid(original);
        scheme.isValid(altered);
        validateIds(original, altered);
        Map<Signature, Delta> deltaMap = new HashMap<>();
        try {
            MapPatch.Builder builder = new MapPatch.Builder(original.getForClass(), original.getID(), original.getVersion(), altered.getVersion(), scheme);
            for (Signature signature : scheme.getPatchSignatures()) {
                Method property = signature.getMethod();
                Object originalValue = property.invoke(original.getValue());
                Object alteredValue = property.invoke(altered.getValue());
                if (!originalValue.equals(alteredValue)) {
                    Delta delta = deltaPatcherManager.createDelta(originalValue, alteredValue);
                    deltaMap.put(signature, delta);
                }
            }
            builder.setPatchMap(deltaMap);
            return builder.build();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            scheme.invalidate();
            throw new PatcherException(e);
        }
    }

    @Override
    public Patch invert(Patch patch) throws PatcherException {
        scheme.isValid(patch);
        MapPatch.Builder builder = new MapPatch.Builder(scheme.getForClass(), patch.getID(), scheme);
        for (Signature signature : scheme.getPatchSignatures()) {
            Delta patchDelta = patch.getDeltaFor(signature);
            Delta invertedDela = deltaPatcherManager.invert(patchDelta);
            builder.putPatch(signature, invertedDela);
        }
        builder.setFromVersion(patch.getToVersion());
        builder.setToVersion(patch.getFromVersion());
        return builder.build();
    }

    @Override
    public Class getForClass() {
        return scheme.getForClass();
    }
}
