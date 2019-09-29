package com.lightbox.jmkv.avl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Avl tree implementation.
 */
public final class AvlTree implements Map<Integer, String> {

    /**
     * Reference to root element.
     */
    private TreeNode root;

    /**
     * Ctor.
     */
    @SuppressWarnings("JavadocMethod")
    public AvlTree(final Integer key, final String value) {
        this.root = new TreeNode(key, value, 0);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(final Object key) {
        return false;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public String get(final Object key) {
        return null;
    }

    @Override
    public String put(final Integer key, final String value) {
        return null;
    }

    @Override
    public String remove(final Object key) {
        return null;
    }

    @Override
    public void putAll(final Map<? extends Integer, ? extends String> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }
}
