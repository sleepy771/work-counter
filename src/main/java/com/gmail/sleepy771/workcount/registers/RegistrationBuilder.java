package com.gmail.sleepy771.workcount.registers;

/**
 * Created by filip on 5/24/15.
 */
public class RegistrationBuilder<K, R extends Registrable> {

    private K key;

    private final R registrable;

    private Register register;

    public RegistrationBuilder(R registrable) {
        this.registrable = registrable;
    }

    public RegistrationBuilder as(K key) {
        this.key = key;
        return this;
    }

    public RegistrationBuilder in(Register register) {
        this.register = register;
        return this;
    }

    public void performRegistration() {
        this.register.register(key, registrable);
        registrable.register(register);
    }
}
