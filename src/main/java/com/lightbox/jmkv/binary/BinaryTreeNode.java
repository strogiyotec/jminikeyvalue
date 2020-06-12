package com.lightbox.jmkv.binary;

import com.lightbox.jmkv.PlainTreeNode;
import com.lightbox.jmkv.TreeNode;

/**
 * Binary Tree Node representation.
 */
public class BinaryTreeNode extends PlainTreeNode {

    /**
     * Root of node.
     */
    private TreeNode parent;

    /**
     * Left child of node.
     */
    private TreeNode left;

    /**
     * Right child of node.
     */
    private TreeNode right;

    /**
     * Ctor for node with parent and without children.
     *
     * @param parent Root
     * @param key    Key
     * @param value  Value
     */
    public BinaryTreeNode(
            final TreeNode parent,
            final Integer key,
            final String value
    ) {
        super(key, value);
        this.parent = parent;
        this.left = null;
        this.right = null;
    }

    /**
     * Ctor.
     *
     * @param key    Key
     * @param value  Value
     * @param parent Root
     * @param left   Left
     * @param right  Right
     */
    public BinaryTreeNode(
            final Integer key,
            final String value,
            final TreeNode parent,
            final TreeNode left,
            final TreeNode right
    ) {
        super(key, value);
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    /**
     * Ctor for Root without reference to parent.
     *
     * @param key   Key
     * @param value Value
     */
    public BinaryTreeNode(final Integer key, final String value) {
        super(key, value);
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    @Override
    public void setLeft(final Integer key, final String value) {
        this.setLeft(new BinaryTreeNode(key, value));
    }

    @Override
    public void setRight(final Integer key, final String value) {
        this.setRight(new BinaryTreeNode(key, value));
    }

    @Override
    public void setLeft(final TreeNode left) {
        this.left = left;
        if (this.left != null) {
            this.left.setParent(this);
        }
    }

    @Override
    public void setRight(final TreeNode right) {
        this.right = right;
        if (this.right != null) {
            this.right.setParent(this);
        }
    }


    @Override
    public void setParent(final TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode left() {
        return this.left;
    }

    @Override
    public TreeNode right() {
        return this.right;
    }

    @Override
    public TreeNode parent() {
        return this.parent;
    }

    public void setLeft(final BinaryTreeNode node) {
        this.left = node;
        if (this.left != null) {
            this.left.setParent(this);
        }
    }

    public void setRight(final BinaryTreeNode node) {
        this.right = node;
        this.right.setParent(this);
    }

    public void setParent(final BinaryTreeNode node) {
        this.parent = node;
    }
}
