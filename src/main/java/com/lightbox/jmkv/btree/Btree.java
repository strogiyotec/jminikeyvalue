package com.lightbox.jmkv.btree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * B-tree implementation.
 * Notes
 * 1) Number of children [B,2B) (false for root node)
 * 2) Number of keys [B-1,2B-1)
 */
public final class Btree implements Map<Integer, String> {

    private final int branchingNumber;

    private final AtomicInteger size = new AtomicInteger(0);

    /**
     * Reference to root.
     */
    private BtreeNode root;

    /**
     * Ctor.
     *
     * @param branchingFactor Order
     */
    public Btree(final int branchingFactor) {
        this.branchingNumber = branchingFactor;
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
            this.root = new BtreeNode(this.maxKeys(), this.maxChildren());
            this.root.addKey(key, value);
            return value;
        } else {
            BtreeNode node = this.root;
            while (node != null) {
                if (node.children() == 0) {
                    node.addKey(key, value);
                    if (node.keys() <= this.maxKeys()) {
                        //OK
                        break;
                    } else {
                        this.split(node);
                        break;
                    }
                }
            }
        }
        return value;
    }

    private void split(final BtreeNode toSplit) {
        final BtreeNode node = toSplit;

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

    private int minKeys() {
        return this.branchingNumber - 1;
    }

    private int maxKeys() {
        return this.branchingNumber * 2 - 2;
    }

    private int minChildren() {
        return this.branchingNumber;
    }

    private int maxChildren() {
        return this.branchingNumber * 2 - 1;
    }


}
