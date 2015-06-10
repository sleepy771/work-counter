package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 2.5.2015.
 */
public class MethodSignature implements Signature {

    public static final String CONSTRUCTOR = "#CONSTRUCTOR";

    private final String methodName;
    private final Class[] arguments;
    private final Class returnType;
    private final Class declaringClass;
    private volatile int hash;

    // remove this one
    public MethodSignature(Class type) {
        this(type, "", new Class[0], type);
    }

    public MethodSignature(Class clazz, Class[] parameters) {
        this(clazz, CONSTRUCTOR, parameters, clazz);
    }

    public MethodSignature(Method method) {
        this(method.getDeclaringClass(), method.getName(), method.getParameterTypes(), method.getReturnType());
    }

    public MethodSignature(Class declaringClass, String methodName, Class[] argumentTypes, Class returnType) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.arguments = new Class[argumentTypes.length];
        System.arraycopy(argumentTypes, 0, arguments, 0, argumentTypes.length);
        this.declaringClass = declaringClass;
    }

    public MethodSignature(Class declaringClass, String methodName, Class returnType) {
        this(declaringClass, methodName, new Class[0], returnType);
    }

    @Override
    public boolean equals(Object o) {
        if (o.hashCode() != this.hashCode() || !MethodSignature.class.equals(o.getClass()))
            return false;
        MethodSignature methodSignatureObject = (MethodSignature) o;
        return methodName.equals(methodSignatureObject.methodName) &&
                returnType.equals(methodSignatureObject.returnType) &&
                compareParameters(methodSignatureObject.arguments);
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
            int hashCode = 31 * 17 + declaringClass.hashCode();
            hashCode = 31 * hashCode + methodName.hashCode();
            hashCode = 31 * hashCode + returnType.hashCode();
            hashCode = 31 * hashCode + computeParametersHashCode();
            hash = hashCode;
        }
        return hash;
    }

    public String getName() {
        return methodName;
    }

    public Class[] getParametersType() {
        Class[] parameters = new Class[arguments.length];
        System.arraycopy(arguments, 0, parameters, 0, arguments.length);
        return parameters;
    }

    @SuppressWarnings("unchecked")
    public Method getMethod() throws NoSuchMethodException {
        if ("".equals(methodName))
            throw new NoSuchMethodException("MethodSignature is for object only");
        return declaringClass.getMethod(methodName, this.arguments);
    }

    public Class getType() {
        return returnType;
    }

    private int computeParametersHashCode() {
        int paramsHashCode = 17;
        for (Class argument : arguments) {
            paramsHashCode = paramsHashCode * 31 + argument.hashCode();
        }
        return paramsHashCode;
    }

    @Override
    public Class getForClass() {
        return declaringClass;
    }
}
