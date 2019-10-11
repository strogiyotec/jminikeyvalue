package com.lightbox.jmkv.btree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * B-tree implementation.
 */
public final class Btree implements Map<Integer, String> {

    /**
     * Height of the tree.
     */
    private final int height;

    /**
     * Length of key-value pairs in single node.
     */
    private final int length;

    /**
     * Reference to root.
     */
    private BtreeNode root;

    /**
     * Ctor.
     *
     * @param height Height
     * @param length Length
     */
    public Btree(final int height, final int length) {
        this.height = height;
        this.length = length;
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

    /**
     * Insert new node to Btree.
     *
     * @param node   Current node , root be default
     * @param key    Key
     * @param value  Value
     * @param height Height
     */
    private static void insert(
            final BtreeNode node,
            final Integer key,
            final String value,
            final int height
    ) {
    }
}
