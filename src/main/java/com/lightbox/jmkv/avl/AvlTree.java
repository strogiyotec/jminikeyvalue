/*
package com.lightbox.jmkv.avl;

import com.lightbox.jmkv.binary.BinaryTree;

*/
/**
 * Avl tree implementation.
 *//*

public final class AvlTree extends BinaryTree {

    */
/**
     * Reference to parent element.
     *//*

    private TreeNodeWithHeight root;

    */
/**
     *
     *//*

    public AvlTree() {
    }

    public AvlTree(final TreeNodeWithHeight root) {
        this.size = 1;
        this.root = root;
        super.root = this.root;
    }

    @Override
    public String put(final Integer key, final String value) {
        if (this.root == null) {
            this.root = new AvlNode(key, value, 0);
            this.size = 1;
            super.root = this.root;
        } else {
            this.putAndBalance(key, value);
        }
    }

    private void putAndBalance(final Integer key, final String value) {

    }

    @Override
    public void clear() {

    }
}

*/
