package com.gmail.sleepy771.workcount.diff.patchers;

import com.gmail.sleepy771.workcount.diff.default_patchables.Patchable;
import com.gmail.sleepy771.workcount.diff.default_patchables.SimplePatchable;
import com.gmail.sleepy771.workcount.diff.default_patches.Delta;
import com.gmail.sleepy771.workcount.diff.default_patches.MapPatch;
import com.gmail.sleepy771.workcount.diff.default_patches.Patch;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;
import com.gmail.sleepy771.workcount.diff.exceptions.PatcherException;
import com.gmail.sleepy771.workcount.diff.reflection.Classy;
import com.gmail.sleepy771.workcount.diff.reflection.MethodSignature;
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
    @SuppressWarnings("unchecked")
    public Patchable patch(Patchable original, Patch patch) throws PatcherException {
        scheme.isValid(patch);
        scheme.isValid(original);
        validateIds(original, patch);
        validateFromVersion(original, patch);
        Map<MethodSignature, Object> patchedMap = new HashMap<>();
        Object originalObject = original.getValue();
        for (MethodSignature methodSignature : scheme.getPatchSignatures()) {
            try {
                Method property = original.getForClass().getMethod(methodSignature.getName(), methodSignature.getParametersType());
                Object value = property.invoke(originalObject);
                if (patch.hasDeltaFor(methodSignature)) {
                    if (patch.isPatch(methodSignature)) {
                        if (!patcherManager.isRegisteredForKey(methodSignature.getType())) {
                            // atempt to run ClassInspector
                            // TODO fire Inspection event on class [MethodSignature::getType]
                        }
                        Patcher patcher = patcherManager.get(methodSignature.getType());
                        Patchable originalInnerObjectPatchable = new SimplePatchable(original.getID(), original.getVersion(), value);
                        Patch innerObjectPatch = (Patch) patch.getDeltaFor(methodSignature);
                        Patchable patchedInnerObjectPatchable = patcher.patch(originalInnerObjectPatchable, innerObjectPatch);
                        patchedMap.put(methodSignature, patchedInnerObjectPatchable.getValue());
                    } else {
                        DeltaPatcher deltaPatcher = deltaPatcherManager.get(methodSignature.getType());
                        Delta delta = patch.getDeltaFor(methodSignature);
                        Object patchedInnerObject = deltaPatcher.patch(value, delta);
                        patchedMap.put(methodSignature, patchedInnerObject);
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
            private Map<MethodSignature, Object> excluded = selectByKeys(patchedMap, scheme.getUnsettableProperties());

            @Override
            protected Object getCallback(Method method) {
                MethodSignature methodMethodSignature = new MethodSignature(method);
                if (excluded.containsKey(methodMethodSignature))
                    return (FixedValue) () -> excluded.get(methodMethodSignature);
                return NoOp.INSTANCE;
            }
        };
        enhancer.setCallbackFilter(helper);
        enhancer.setCallbacks(helper.getCallbacks());
        Object patchedObject = enhancer.create();
        for (Map.Entry<MethodSignature, MethodSignature> setterToProperty : scheme.getSetterSignatures().entrySet()) {
            try {
                Method setter = scheme.getForClass().getMethod(setterToProperty.getKey().getName(), setterToProperty.getKey().getParametersType());
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

    private Map<MethodSignature, Object> selectByKeys(Map<MethodSignature, Object> patchableMap, Set<MethodSignature> methodSignatureSet) {
        Map<MethodSignature, Object> selected = new HashMap<>();
        for (MethodSignature sgn : methodSignatureSet) {
            selected.put(sgn, patchableMap.get(sgn));
        }
        return selected;
    }

    @Override
    public Patch createPatch(Patchable original, Patchable altered) throws PatcherException {
        scheme.isValid(original);
        scheme.isValid(altered);
        validateIds(original, altered);
        Map<MethodSignature, Delta> deltaMap = new HashMap<>();
        try {
            MapPatch.Builder builder = new MapPatch.Builder(original.getForClass(), original.getID(), original.getVersion(), altered.getVersion(), scheme);
            for (MethodSignature methodSignature : scheme.getPatchSignatures()) {
                Method property = methodSignature.getMethod();
                Object originalValue = property.invoke(original.getValue());
                Object alteredValue = property.invoke(altered.getValue());
                if (!originalValue.equals(alteredValue)) {
                    Delta delta = deltaPatcherManager.createDelta(originalValue, alteredValue);
                    deltaMap.put(methodSignature, delta);
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
        for (MethodSignature methodSignature : scheme.getPatchSignatures()) {
            Delta patchDelta = patch.getDeltaFor(methodSignature);
            Delta invertedDela = deltaPatcherManager.invert(patchDelta);
            builder.putPatch(methodSignature, invertedDela);
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
