package com.gmail.sleepy771.workcount.diff.numbers;

/**
 * Created by filip on 1.5.2015.
 */
public abstract class AbstractNumberHandler<T extends Number> implements NumberHandler<T> {
    private final Class<T> numberClass;
    private final String representation;

    public AbstractNumberHandler(Class<T> numberClass, String representation) {
        this.representation = representation;
        this.numberClass = numberClass;
    }

    @Override
    public Class<T> getNumberClass() {
        return numberClass;
    }

    @Override
    public String getRepresentation() {
        return representation;
    }

    @Override
    public String asString(Number number) {
        return representation + "#" + number.toString();
    }

    @Override
    public void checkNumberType(Number n) {
        if (!getNumberClass().equals(n.getClass()))
            throw new IllegalArgumentException("Invalid number class type");
    }

    @Override
    public T cast(Number n) {
        checkNumberType(n);
        return numberClass.cast(n);
    }

    @Override
    public T fromString(String numberRep) {
        String[] typeAndValue = numberRep.split("#", 1);
        if (!typeAndValue[0].equals(representation)) {
            throw new IllegalArgumentException("Invalid representation type. Got " + typeAndValue[0] + ", but expected " + representation);
        }
        return parseNumber(typeAndValue[1]);
    }

    protected abstract T parseNumber(String string);
}
