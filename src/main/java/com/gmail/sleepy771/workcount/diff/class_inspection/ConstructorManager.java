package com.gmail.sleepy771.workcount.diff.class_inspection;

import com.gmail.sleepy771.workcount.diff.DefaultAbstractManager;
import com.gmail.sleepy771.workcount.diff.default_constructor_parameters.ConstructorParameters;

/**
 * Created by filip on 6/4/15.
 */
public class ConstructorManager extends DefaultAbstractManager<Class, ConstructorParameters> {
    @Override
    protected Class getKeyForElement(ConstructorParameters element) {
        return element.getForClass();
    }
}
