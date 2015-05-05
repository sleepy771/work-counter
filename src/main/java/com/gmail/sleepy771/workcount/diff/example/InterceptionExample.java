package com.gmail.sleepy771.workcount.diff.example;
import com.gmail.sleepy771.workcount.diff.reflection.Signature;
import net.sf.cglib.proxy.*;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 1.5.2015.
 */
public class InterceptionExample {

    public static void main(String[] args) {
        final Map<Signature, FixedValue> interceptedMethodsMap = new HashMap<>();
        interceptedMethodsMap.put(new Signature("getName", new Class[0], String.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Rudolph";
            }
        });
        interceptedMethodsMap.put(new Signature("getAge", new Class[0], int.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return 13;
            }
        });
        interceptedMethodsMap.put(new Signature("getCountry", new Class[0], String.class), new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Sweden";
            }
        });
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(InterceptedClass.class);
        CallbackHelper helper = new CallbackHelper(InterceptedClass.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                System.out.println(method.getName());
                Signature sgn = new Signature(method);
                if (interceptedMethodsMap.containsKey(sgn))
                    return interceptedMethodsMap.get(sgn);
                return NoOp.INSTANCE;
            }
        };
        enhancer.setCallbacks(helper.getCallbacks());
        enhancer.setCallbackFilter(helper);
        InterceptedClass example = (InterceptedClass) enhancer.create();
        long time = System.nanoTime();
        System.out.println(example.toString());
        time -= System.nanoTime();
        System.out.println("duration: " + (-time) + " ns");
    }
}
