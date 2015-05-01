package com.gmail.sleepy771.workcount.mapping;

import java.util.HashSet;

/**
 * Created by filip on 19.4.2015.
 */
public class Mapping<T, R> {
    private static class MLink<S, K> {
        private S node;
        private HashSet<MLink<K, S>> linkedTo;

        public S getNode() {
            return node;
        }

        public HashSet<MLink<K, S>> getLinkedTo() {
            return linkedTo;
        }

        public void linkTo(MLink<K, S> link) {
            linkedTo.add(link);
            link.linkAdd(this);
        }

        private void linkAdd(MLink<K, S> link) {
            linkedTo.add(link);
        }

        public void unlink(MLink<K, S> link) {
            linkedTo.remove(link);
            link.linkRemove(this);
        }

        private void linkRemove(MLink<K, S> link) {
            linkedTo.remove(link);
        }

        public int linksCount() {
            return linkedTo.size();
        }

        public void unlinkAll() {

        }
    }

    private HashSet<T> from;
    private HashSet<R> to;

    public void addFrom(T obj) {
        from.add(obj);
    }

    public void addTo(R obj) {
        to.add(obj);
    }

    public void linkObjects(T obj1, R obj2) {

    }
}
