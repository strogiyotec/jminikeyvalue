package com.lightbox.jmkv.btree;

/**
 * Node of Btree.
 */
final class BtreeNode {

    /**
     * Amount of children.
     * Usually marked as 'm' in tutorials
     */
    private final int childrenAmount;

    /**
     * Array of children of current node.
     */
    private final NodeEntry[] children;

    /**
     * Ctor.
     *
     * @param childrenAmount Amount of children
     */
    BtreeNode(final int childrenAmount) {
        this.childrenAmount = childrenAmount;
        this.children = new NodeEntry[this.childrenAmount];
    }
}
