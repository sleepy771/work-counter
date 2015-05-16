package com.gmail.sleepy771.workcount.diff.datastructures;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Created by filip on 5/16/15.
 */
public class HashableList<T> implements List<T> {

    private final List<T> innerList;

    public HashableList(List<T> list) {
        this.innerList = list;
    }

    public HashableList(T[] array) {
        this(Arrays.asList(array));
    }

    @Override
    public int size() {
        return innerList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return innerList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return innerList.iterator();
    }

    @Override
    public Object[] toArray() {
        return innerList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return innerList.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return innerList.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return innerList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return innerList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return innerList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return innerList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return innerList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return innerList.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        innerList.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        innerList.sort(c);
    }

    @Override
    public void clear() {
        innerList.clear();
    }

    @Override
    public T get(int index) {
        return innerList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return innerList.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        innerList.add(index, element);
    }

    @Override
    public T remove(int index) {
        return innerList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return innerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return innerList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return innerList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return innerList.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return innerList.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<T> spliterator() {
        return innerList.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (!List.class.isInstance(o))
            return false;
        List other = (List) o;
        if (other.size() != size())
            return false;
        return compareLists(other);
    }

    private boolean compareLists(List lst) {
        Iterator<T> thisListIter = iterator();
        Iterator<Object> otherListIter = lst.iterator();
        while (thisListIter.hasNext() && otherListIter.hasNext()) {
            if (!thisListIter.next().equals(otherListIter.next()))
                return false;
        }
        return thisListIter.hasNext() == otherListIter.hasNext();
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        for (T obj : innerList) {
            hashCode = hashCode * 31 + obj.hashCode();
        }
        return hashCode;
    }
}
