package com.gmail.sleepy771.workcount.diff.numbers;

/**
 * Created by filip on 1.5.2015.
 */
public interface NumberHandler<T extends Number> {

    Class<T> getNumberClass();

    String asString(Number number);

    String getRepresentation();

    T fromString(String numberRep);

    T difference(Number original, Number altered);

    T addition(Number original, Number patch);

    T negative(Number number);

    void checkNumberType(Number n);

    T cast(Number n);
}
