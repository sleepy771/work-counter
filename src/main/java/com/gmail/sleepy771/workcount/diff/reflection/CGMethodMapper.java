package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.patchers.ObjectPatcher;
import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 6/19/15.
 */
public class CGMethodMapper implements Classy {

    Scheme classScheme;

    private Map<Property, Object> propertyValuesMap;

    private Map<Signature, Object> signatureValuesMap;

    private Map<Signature, Object> signatureMap;

    private boolean lock;

    public CGMethodMapper() {
        propertyValuesMap = new HashMap<>();
        lock = false;
    }


    public void set(Property property, Object value) {
        // TODO imprve exceptions
        if (isLocked())
            throw new IllegalArgumentException();
        if (!isValidGetterProperty(property))
            throw new IllegalArgumentException();
        if (propertyValuesMap.containsKey(property))
            throw new IllegalArgumentException();
        propertyValuesMap.put(property, value);
    }

    public void set(Signature signature, Object value) throws Exception {
        if (!getForClass().isAssignableFrom(signature.getForClass()))
            throw new IllegalArgumentException();
        if (!signature.getType().isInstance(value)) {
            throw new IllegalArgumentException();
        }
        signatureMap.put(signature, value);
    }

    private boolean isValidGetterProperty(Property property) {
        return classScheme.isValidGetterProperty(property);
    }

    public Map<Signature, Object> getValueMap() {
        if (isLocked() && signatureValuesMap != null) {
            return signatureValuesMap;
        }
        Map<Signature, Object> functionMap = new HashMap<>();
        for (Map.Entry<Property, Object> entry : propertyValuesMap.entrySet()) {
            functionMap.put(entry.getKey().getAccessor().getGetter().getGetterSignature(), entry.getValue());
        }
        if (isLocked())
            signatureValuesMap = Collections.unmodifiableMap(functionMap);
        return Collections.unmodifiableMap(functionMap);
    }

    public void lock() {
        lock = true;
    }

    public boolean isLocked() {
        return lock;
    }

    public void validate() {

    }

    @Override
    public Class getForClass() {
        return classScheme.getForClass();
    }
}
