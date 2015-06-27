package com.gmail.sleepy771.workcount.diff.reflection;

import java.lang.reflect.Method;

/**
 * Created by filip on 6/22/15.
 */
public class CGPropertySetter extends AbstractPropertySetter {

    protected CGPropertySetter(Method getterProperty, Property property) {
        super(property, new MethodSignature(getterProperty));
    }

    @Override
    protected void setDirectly(Object destination, Object value) throws Exception {
        if (!CGMethodMapper.class.isInstance(destination))
            throw new IllegalArgumentException();
        CGMethodMapper mapper = (CGMethodMapper) destination;
        mapper.set(getProperty(), value);
    }
}
