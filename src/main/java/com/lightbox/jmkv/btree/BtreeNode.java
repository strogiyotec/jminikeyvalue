package com.lightbox.jmkv.btree;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Node of Btree.
 * Constraints:
 * t - minDegree
 * 1) Every non root node must have at least t-1 keys
 * 2) Every node must have at most 2t-1 keys
 * 3) Every internal node(without root) must have at least t children
 * And at most 2t children
 */
final class BtreeNode {

    /**
     * Reference to parent.
     */
    private final BtreeNode parent;

    /**
     * Array of keys.
     */
    private final NodeEntry[] keys;

    /**
     * Array of children.
     */
    private final BtreeNode[] children;

    /**
     * Size of keys.
     */
    private final AtomicInteger keysSize = new AtomicInteger(0);

    /**
     * Size of children.
     */
    private final AtomicInteger childrenSize = new AtomicInteger(0);

    /**
     * Ctor.
     */
    public BtreeNode(final BtreeNode parent, final int keys, final int children) {
        this.parent = parent;
        this.keys = new NodeEntry[keys];
        this.children = new BtreeNode[children];
    }

    /**
     * Root ctor.
     */
    public BtreeNode(final int keys, final int children) {
        this.parent = null;
        this.keys = new NodeEntry[keys];
        this.children = new BtreeNode[children];
    }

    /**
     * Add key and sort array of keys
     *
     * @param key   Key
     * @param value Value
     */
    public void addKey(final Integer key, final String value) {
        this.keys[this.keysSize.getAndIncrement()] = new NodeEntry(key, value);
        Arrays.sort(this.keys, 0, this.keysSize.get());
    }

    /**
     * Amount of children.
     *
     * @return Amount of children
     */
    public int children() {
        return this.childrenSize.get();
    }

    /**
     * Amount of keys.
     *
     * @return Amount of keys
     */
    public int keys() {
        return this.keysSize.get();
    }
}
