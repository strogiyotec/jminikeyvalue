package com.lightbox.jmkv.btree;

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
     * Amount of children.
     * Usually marked as 'm' in tutorials
     */
    int childrenAmount;

    /**
     * Array of children of current node.
     */
    final BtreeNode[] children;

    /**
     * Array of keys.
     */
    final NodeEntry[] keys;

    /**
     * Ctor.
     *
     * @param minDegree Min degree for node
     */
    BtreeNode(final int minDegree) {
        if (minDegree <= 2) {
            throw new IllegalStateException("Min value for degree is 2");
        }
        this.childrenAmount = 0;
        this.children = new BtreeNode[minDegree * 2];
        this.keys = new NodeEntry[minDegree * 2 - 1];
    }

}
