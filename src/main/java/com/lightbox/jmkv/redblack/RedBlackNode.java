/*
package com.lightbox.jmkv.redblack;

import com.lightbox.jmkv.PlainTreeNode;

public final class RedBlackNode extends PlainTreeNode {

    private RedBlackNode left;

    private RedBlackNode right;

    private RedBlackNode parent;

    private boolean red = true;

    public RedBlackNode(final Integer key, final String value) {
        super(key, value);
    }

    @Override
    protected PlainTreeNode parent() {
        return this.parent;
    }

    @Override
    public void setLeft(final Integer key, final String value) {
        this.setLeft(new RedBlackNode(key, value));
    }

    @Override
    public void setRight(final Integer key, final String value) {

    }


    public void setLeft(RedBlackNode node) {
        this.left = node;
        this.left.setParent(this);
    }

    public void setRight(RedBlackNode node) {
        this.right = node;
        this.right.setParent(this);
    }

    public void setParent(RedBlackNode node) {
        this.parent = node;
    }

    @Override
    protected PlainTreeNode left() {
        return this.left;
    }

    @Override
    protected PlainTreeNode right() {
        return this.right;
    }
}
*/
