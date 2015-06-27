package com.gmail.sleepy771.workcount.diff.reflection;

import com.gmail.sleepy771.workcount.diff.scheme.Scheme;

/**
 * Created by filip on 6/19/15.
 */
public class CGMethodInjector {

    private CGMethodMapper mapper;
    private Scheme scheme;

    public CGMethodInjector(CGMethodMapper mapper, Scheme scheme) {
        this.scheme = scheme;
        this.mapper = mapper;
    }
}
