package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 2.5.2015.
 */
public class Signature {

    private final String methodName;
    private final Class[] arguments;
    private final Class returnType;
    private volatile int hash;

    public Signature(Method method) {
        this(method.getName(), method.getParameterTypes(), method.getReturnType());
    }

    public Signature(String methodName, Class[] argumentTypes, Class returnType) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.arguments = new Class[argumentTypes.length];
        System.arraycopy(argumentTypes, 0, arguments, 0, argumentTypes.length);
    }

    @Override
    public boolean equals(Object o) {
        if (!Signature.class.equals(o.getClass()))
            return false;
        Signature signatureObject = (Signature) o;
        return methodName.equals(signatureObject.methodName) &&
                returnType.equals(signatureObject.returnType) &&
                compareParameters(signatureObject.arguments);
    }

    private boolean compareParameters(Class[] params) {
        if (arguments.length != params.length)
            return false;
        for (int k = 0; k < arguments.length; k++) {
            if (!arguments[k].equals(params[k]))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            int hashCode = 17;
            hashCode = 31 * hashCode + methodName.hashCode();
            hashCode = 31 * hashCode + returnType.hashCode();
            hashCode = 31 * hashCode + computeParametersHashCode();
            hash = hashCode;
        }
        return hash;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParametersType() {
        Class[] parameters = new Class[arguments.length];
        System.arraycopy(arguments, 0, parameters, 0, arguments.length);
        return parameters;
    }

    public Class getReturnType() {
        return returnType;
    }

    private int computeParametersHashCode() {
        int paramsHashCode = 17;
        for (Class argument : arguments) {
            paramsHashCode = paramsHashCode * 31 + argument.hashCode();
        }
        return paramsHashCode;
    }
}
