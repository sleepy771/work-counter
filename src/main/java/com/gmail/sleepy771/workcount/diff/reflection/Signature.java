package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 1.5.2015.
 */
public class Signature {
    private volatile int hashCode;
    private String methodName;
    private Class[] paramTypes;
    private Class returnType;

    public Signature(String name, Class[] paramTypes, Class returnType) {
        this.methodName = name;
        this.paramTypes = new Class[paramTypes.length];
        System.arraycopy(paramTypes, 0, this.paramTypes, 0, paramTypes.length);
        this.returnType = returnType;
    }

    public Signature(Method method) {
        this(method.getName(), method.getParameterTypes(), method.getReturnType());
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(Signature.class)) {
            return false;
        }
        Signature other = (Signature) o;
        return other.methodName.equals(methodName) && compareArguments(other.paramTypes) && returnType.equals(other.returnType);
    }

    public String getMethodName() {
        return methodName;
    }

    public Class getReturnType() {
        return returnType;
    }

    public Class getArgumentType(int k) {
        return paramTypes[k];
    }

    public Class[] getParamTypes() {
        Class[] array = new Class[paramTypes.length];
        System.arraycopy(paramTypes, 0, array, 0, paramTypes.length);
        return array;
    }

    private boolean compareArguments(Class[] classes) {
        if (classes.length != paramTypes.length)
            return false;
        for (int k = 0; k < this.paramTypes.length; k++) {
            if (!classes[k].equals(paramTypes[k]))
                return false;
        }
        return true;
    }

    public int hashCode() {
        if (hashCode == 0) {
            int result = 17 * 31 + methodName.hashCode();
            result = result * 31 + computeParamsHash();
            result = result * 31 + returnType.hashCode();
            hashCode = result;
        }
        return hashCode;
    }

    private int computeParamsHash() {
        int argsHash = 17;
        for (Class argumentType : paramTypes) {
            argsHash = argsHash * 31 + argumentType.hashCode();
        }
        return argsHash;
    }
}
