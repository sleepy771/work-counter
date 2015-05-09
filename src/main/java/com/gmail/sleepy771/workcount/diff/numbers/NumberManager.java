package com.gmail.sleepy771.workcount.diff.numbers;

import com.gmail.sleepy771.workcount.Manager;
import com.gmail.sleepy771.workcount.diff.exceptions.ManagerException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by filip on 1.5.2015.
 */
public class NumberManager implements Manager<Class<? extends Number>, NumberHandler<? extends Number>>{

    private static NumberManager INSTANCE = null;

    private final Map<Class<? extends Number>, NumberHandler<? extends Number>> classParsersMap;
    private final Map<String, NumberHandler<? extends Number>> stringParsersMap;

    private NumberManager() {
        classParsersMap = new HashMap<>();
        stringParsersMap = new HashMap<>();
        populateParsers();
    }

    public void registerParser(NumberHandler<? extends Number> parser) {
        registerParser(parser.getNumberClass(), parser.getRepresentation(), parser);
    }

    public void registerParser(Class<? extends Number> clazz, String representation, NumberHandler<? extends Number> parser) {
        if (classParsersMap.containsKey(clazz))
            throw new IllegalArgumentException("Parser for class already registered");
        if (stringParsersMap.containsKey(representation))
            throw new IllegalArgumentException("Parser for representation already registered");
        classParsersMap.put(clazz, parser);
        stringParsersMap.put(representation, parser);
    }

    public void unregisterParsers(NumberHandler<? extends Number> parser) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void releaseAll() {
        classParsersMap.clear();
        stringParsersMap.clear();
    }

    public NumberHandler<? extends Number> unregisterParser(Class<? extends Number> clazz) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void populateParsers() {
        registerParser(new AbstractNumberHandler<Byte>(Byte.class, "byte") {
            @Override
            public Byte difference(Number original, Number altered) {
                return (byte) (cast(altered) - cast(original));
            }

            @Override
            public Byte addition(Number original, Number patch) {
                return (byte) (cast(original) + cast(patch));
            }

            @Override
            public Byte negative(Number number) {
                return (byte)(-cast(number));
            }

            @Override
            protected Byte parseNumber(String string) {
                return Byte.valueOf(string);
            }
        });
        registerParser(new AbstractNumberHandler<Short>(Short.class, "short") {
            @Override
            public Short difference(Number original, Number altered) {
                return (short) (cast(altered) - cast(original));
            }

            @Override
            public Short addition(Number original, Number patch) {
                return (short) (cast(original) + cast(patch));
            }

            @Override
            public Short negative(Number number) {
                return (short) (- cast(number));
            }

            @Override
            protected Short parseNumber(String string) {
                return Short.valueOf(string);
            }
        });
        registerParser(new AbstractNumberHandler<Integer>(Integer.class, "int") {

            @Override
            public Integer difference(Number original, Number altered) {
                return cast(altered) - cast(original);
            }

            @Override
            public Integer addition(Number original, Number patch) {
                return cast(original) + cast(patch);
            }

            @Override
            public Integer negative(Number number) {
                return - cast(number);
            }

            @Override
            protected Integer parseNumber(String string) {
                return Integer.valueOf(string);
            }
        });
        registerParser(new AbstractNumberHandler<Long>(Long.class, "long") {
            @Override
            public Long difference(Number original, Number altered) {
                return cast(altered) - cast(original);
            }

            @Override
            public Long addition(Number original, Number patch) {
                return cast(original) + cast(patch);
            }

            @Override
            public Long negative(Number number) {
                return - cast(number);
            }

            @Override
            protected Long parseNumber(String string) {
                return Long.valueOf(string);
            }
        });
        registerParser(new AbstractNumberHandler<Float>(Float.class, "float") {

            @Override
            public Float difference(Number original, Number altered) {
                return cast(altered) - cast(original);
            }

            @Override
            public Float addition(Number original, Number patch) {
                return cast(original) + cast(patch);
            }

            @Override
            public Float negative(Number number) {
                return - cast(number);
            }

            @Override
            protected Float parseNumber(String string) {
                return Float.valueOf(string);
            }
        });
        registerParser(new AbstractNumberHandler<Double>(Double.class, "double") {

            @Override
            public Double difference(Number original, Number altered) {
                return cast(altered) - cast(original);
            }

            @Override
            public Double addition(Number original, Number patch) {
                return cast(original) + cast(patch);
            }

            @Override
            public Double negative(Number number) {
                return - cast(number);
            }

            @Override
            protected Double parseNumber(String string) {
                return Double.valueOf(string);
            }
        });
    }

    public Number fromString(String representation) {
        String withoutWhitespaces = removeWhitespaces(representation);
        int positionOfSharp = withoutWhitespaces.indexOf('#');
        NumberHandler<? extends Number> parser = stringParsersMap.get(withoutWhitespaces.substring(0, positionOfSharp));
        return parser.fromString(withoutWhitespaces);
    }

    public Number sub(Number n1, Number n2) {
        if (!n1.getClass().equals(n2.getClass())) {
            throw new IllegalArgumentException("Unequal class types");
        }
        NumberHandler<? extends Number> numberHandler = classParsersMap.get(n1.getClass());
        return numberHandler.difference(numberHandler.getNumberClass().cast(n1), numberHandler.getNumberClass().cast(n2));
    }

    public Number add(Number n1, Number n2) {
        if (!n1.getClass().equals(n2.getClass())) {
            throw new IllegalArgumentException("Unequal class types");
        }
        NumberHandler<? extends Number> numberHandler = classParsersMap.get(n1.getClass());
        return numberHandler.addition(n1, n2);
    }

    public Number negative(Number n) {
        return classParsersMap.get(n.getClass()).negative(n);
    }

    public String asString(Number number) {
        NumberHandler<? extends Number> parser = classParsersMap.get(number.getClass());
        return parser.asString(number);
    }

    public String removeWhitespaces(String representation) {
        return representation.replaceAll("\\s+", "");
    }

    public static NumberManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NumberManager();
        }
        return INSTANCE;
    }

    public static void releaseManagerInstance() {
        INSTANCE = null;
    }

    @Override
    public void register(Class<? extends Number> key, NumberHandler<? extends Number> element) throws ManagerException {

    }

    @Override
    public void register(NumberHandler<? extends Number> element) {

    }

    @Override
    public void unregister(NumberHandler<? extends Number> element) {

    }

    @Override
    public boolean isRegistered(NumberHandler<? extends Number> element) {
        return false;
    }

    @Override
    public boolean isRegisteredForKey(Class<? extends Number> key) {
        return false;
    }

    @Override
    public NumberHandler<? extends Number> get(Class<? extends Number> key) {
        return null;
    }

    @Override
    public NumberHandler<? extends Number> remove(Class<? extends Number> key) {
        return null;
    }
}
