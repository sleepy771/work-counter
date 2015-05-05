package com.gmail.sleepy771.workcount.diff.example;

/**
 * Created by filip on 2.5.2015.
 */
public class InterceptedClass {
    public String getName() {
        return "Carl";
    }

    public int getAge() {
        return 10;
    }

    public String getCountry() {
        return "Spain";
    }

    public String toString() {
        return "My name is " + getName() + " and I'm " + getAge() + " old.\nI am from " +getCountry();
    }
}
