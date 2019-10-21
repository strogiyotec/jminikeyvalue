package com.lightbox.jmkv.btree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * B-tree implementation.
 * Notes
 * 1) Number of children [B,2B) (false for root node)
 * 2) Number of keys [B-1,2B-1)
 */
public final class Btree implements Map<Integer, String> {

    /**
     * Height of the tree.
     */
    private final int height;

    /**
     * Reference to root.
     */
    private BtreeNode root;

    /**
     * Ctor.
     *
     * @param height Height
     */
    public Btree(final int height) {
        this.height = height;
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
        if (this.root == null) {
            this.root = new BtreeNode(this.height, key, value);
        } else {
            Btree.insert(this.root, key, value, this.height);
        }
        return value;
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
        if (node.childrenAmount == node.upperBoundIndex()) {
            //The root is full , split it
            final BtreeNode btreeNode = new BtreeNode(node.minDegree(), key, value);

        }
    }
}
