package com.gmail.sleepy771.workcount.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by filip on 18.4.2015.
 */
public class ListUtils {
    public static final <T> LinkedList<T> asLinkedList(Collection<T> c) {
        return new LinkedList<>(c);
    }

    public static final List<String> resize(int size, List<String> strList) {
        for (int i = strList.size(); i < size; i++) {
            strList.add("");
        }
        return strList;
    }
}
