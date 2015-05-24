package com.gmail.sleepy771.workcount.registers;

/**
 * Created by filip on 5/24/15.
 */
public interface Registrable {

    void register(Register register);

    void freeFrom(Register register);

    void free();

    boolean isRegistered();

    boolean isRegisteredIn(Register register);
}
